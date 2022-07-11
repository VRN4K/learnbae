package com.learnbae.my.presentation.screens.searchtranslationscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.gson.Gson
import com.learnbae.my.R
import com.learnbae.my.databinding.*
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.screens.mainscreen.holder.FiveLastWordsHolder
import com.learnbae.my.presentation.screens.searchtranslationscreen.holder.ExamplesWordHolder
import com.learnbae.my.presentation.screens.searchtranslationscreen.holder.TranslationWordHolder
import com.learnbae.my.presentation.screens.updateprofilescreen.UpdateProfileFragment
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
        binding.wordTranslationRecycler.adapter = translationAdapter
        binding.examplesItemsRecycler.adapter = examplesAdapter
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
                    StateData.DataStatus.LOADING -> showLoading(true)
                    StateData.DataStatus.COMPLETE -> with(it.data!!) {
                        binding.apply {
                            wordTitle.text = word
                            wordTranscription.text = requireContext().resources.getString(
                                R.string.transcription_pattern,
                                pronunciations
                            )
                            translationAdapter.swapItems(translation)
                            examplesAdapter.swapItems(examples.toList())
                            showLoading(false)
                        }
                    }
                    else -> return@observe
                }
            }
        }
    }

    private fun showLoading(isShow: Boolean) {
        binding.loadingAnimator.changeLoadingState(isShow, binding.content.id)
    }
}