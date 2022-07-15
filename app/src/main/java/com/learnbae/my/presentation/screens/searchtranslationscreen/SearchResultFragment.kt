package com.learnbae.my.presentation.screens.searchtranslationscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.learnbae.my.R
import com.learnbae.my.databinding.*
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.screens.searchtranslationscreen.holder.ExamplesWordHolder
import com.learnbae.my.presentation.screens.searchtranslationscreen.holder.TranslationWordHolder
import com.learnbae.my.presentation.screens.searchtranslationscreen.holder.WordSoundsHolder
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable

@AndroidEntryPoint
class SearchResultFragment : BaseFragment() {
    companion object {
        private const val WORD_KEY = "WORD SEARCHING"
        private const val SOURCE_LANGUAGE_KEY = "SOURCE LANGUAGE"
        private const val TARGET_LANGUAGE_KEY = "TARGET LANGUAGE"
        fun newInstance(
            sourceLanguage: String,
            targetLanguage: String,
            word: String
        ) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putString(WORD_KEY, word)
                putString(SOURCE_LANGUAGE_KEY, sourceLanguage)
                putString(TARGET_LANGUAGE_KEY, targetLanguage)
            }
        }
    }

    private var binding by onDestroyNullable<SearchResultLayoutBinding>()
    private val viewModel by viewModels<SearchResultViewModel>()

    private val soundsAdapter by lazy {
        SimpleAdapter(
            SoundItemBinding::inflate,
            createViewHolder = { WordSoundsHolder(it) },
            onClickCallback = { item, _ ->
                ExoPlayer.Builder(requireContext()).build().apply {
                    println(item.second)
                    addMediaItem(MediaItem.fromUri(item.second))
                    prepare()
                    play()
                }
            }
        )
    }

    private val translationAdapter by lazy {
        SimpleAdapter(
            WordTranslationItemBinding::inflate,
            createViewHolder = { TranslationWordHolder(it) },
            onClickCallback = { _, _ -> }
        )
    }

    private val examplesAdapter by lazy {
        SimpleAdapter(
            ExamplesItemBinding::inflate,
            createViewHolder = { ExamplesWordHolder(it) },
            onClickCallback = { _, _ -> }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchResultLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            wordTranslationRecycler.adapter = translationAdapter
            examplesItemsRecycler.adapter = examplesAdapter
            soundItemsRecycler.adapter = soundsAdapter
        }
        setListeners()
        setObservers()
        viewModel.searchWordTranslation(
            requireArguments().getString(SOURCE_LANGUAGE_KEY)!!,
            requireArguments().getString(TARGET_LANGUAGE_KEY)!!,
            requireArguments().getString(WORD_KEY)!!
        )
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setObservers() {
        viewModel.apply {
            wordTranslation.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.LOADING -> showContent(false)
                    StateData.DataStatus.COMPLETE -> with(it.data!!) {
                        binding.apply {
                            addToVocabularyButton.setVisibility(true)
                            wordTitle.text = word

                            pronunciations?.let {
                                wordTranscription.setVisibility(true)
                                wordTranscription.text = requireContext().resources.getString(
                                    R.string.transcription_pattern,
                                    it
                                )
                            }

                            translationAdapter.swapItems(translation)
                            examples?.let {
                                examplesTitle.setVisibility(true)
                                examplesBlock.setVisibility(true)
                                examplesAdapter.swapItems(examples.toList())
                            }
                            wordSounds?.let {
                                soundItemsRecycler.setVisibility(true)
                                soundsAdapter.swapItems(wordSounds.toList())
                            }
                            showContent(true)
                        }
                    }
                    StateData.DataStatus.ERROR -> showContent(null)
                    else -> return@observe
                }
            }

            isWordAlreadyInVocabulary.observe(viewLifecycleOwner) {
                binding.addToVocabularyButton.isChecked = it
            }
        }
    }

    private fun setListeners() {
        binding.apply {
            backButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
            addToVocabularyButton.setOnClickListener {
                if (addToVocabularyButton.isChecked) {
                    viewModel.addWordToVocabulary()
                } else {
                    viewModel.removeFromVocabulary()
                }

            }
            researchButton.setOnClickListener { viewModel.navigateToPreviousScreen() }
        }
    }

    private fun showContent(isShow: Boolean?) {
        binding.apply {
            when (isShow) {
                true -> loadingAnimator.visibleChildId = content.id
                false -> loadingAnimator.visibleChildId = progressBarLoading.id
                null -> loadingAnimator.visibleChildId = emptyListState.id
            }
        }
    }
}