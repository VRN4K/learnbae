package com.learnbae.my.presentation.screens.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import com.google.android.exoplayer2.ExoPlayer
import com.learnbae.my.R
import com.learnbae.my.databinding.MainScreenBinding
import com.learnbae.my.databinding.WordsListItemBinding
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.DiffUtilCallBack
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.screens.Screens
import com.learnbae.my.presentation.screens.mainscreen.addworddialog.AddWordDialog
import com.learnbae.my.presentation.screens.mainscreen.holder.FiveLastWordsHolder
import dagger.hilt.android.AndroidEntryPoint
import ltst.nibirualert.my.presentation.common.onDestroyNullable
import java.util.*

@AndroidEntryPoint
class MainScreenFragment : BaseFragment() {
    private var binding by onDestroyNullable<MainScreenBinding>()
    private val viewModel by viewModels<MainScreenViewModel>()

    private val wordsListAdapter by lazy {
        SimpleAdapter(
            WordsListItemBinding::inflate,
            createViewHolder = { FiveLastWordsHolder(it) },
            onClickCallback = { _, _ -> }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lastFiveWordsRecycler.adapter = wordsListAdapter
        setObservers()
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            addButton.setOnClickListener { showAddDialog() }
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (languageCheckbox.isChecked) {
                        viewModel.navigateToScreen(
                            Screens.getSearchResultFragment(
                                "ru",
                                "en",
                                query!!
                            )
                        )
                    } else {
                        viewModel.navigateToScreen(
                            Screens.getSearchResultFragment(
                                "en",
                                "ru",
                                query!!
                            )
                        )
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            languageCheckbox.setOnCheckedChangeListener { _, check -> showSearchHint(check) }
        }
    }

    private fun setObservers() {
        viewModel.apply {
            wordOfADay.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.LOADING -> showLoading(true)
                    StateData.DataStatus.COMPLETE -> with(it.data) {
                        showLoading(false)
                        showWordOfADay(this!!)
                    }
                    else -> return@observe
                }
            }

            countTitle.observe(viewLifecycleOwner) {
                showCountWordsTitle(false, it)
            }

            vocabulary.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.EMPTY_LIST -> showCountWordsTitle(true).apply {
                        showFiveLastWords(false)
                    }
                    StateData.DataStatus.COMPLETE -> with(it.data) {
                        wordsListAdapter.swapItems(this!!)
                        showFiveLastWords(true)
                    }
                    else -> return@observe
                }
            }

            isWordAlreadyInVocabulary.observe(viewLifecycleOwner) {
                binding.wordOfADayBlock.setAddToVocabularyCheckStatus(it)
            }

            mediaSourceData.observe(viewLifecycleOwner) {
                ExoPlayer.Builder(requireContext()).build().apply {
                    addMediaItem(it)
                    prepare()
                    play()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.wordOfADayBlock.changeLoadingState(isLoading)
    }

    private fun showFiveLastWords(isShown: Boolean) {
        binding.lastFiveWordsBlock.visibility = if (isShown) View.VISIBLE else View.GONE
    }

    private fun showCountWordsTitle(isEmpty: Boolean, wordsCount: Int = 0) {
        requireContext().apply {
            binding.vocabularyTitle.text = if (isEmpty) {
                getString(R.string.words_list_empty_text)
            } else {
                getString(
                    R.string.words_list_not_empty_text, wordsCount.toString(),
                    resources.getQuantityString(
                        R.plurals.words_plurals,
                        wordsCount
                    )
                )
            }
        }
    }

    private fun showSearchHint(isLangButtonChecked: Boolean) {
        binding.searchView.queryHint =
            resources.getString(if (isLangButtonChecked) R.string.search_word_in_russian_title else R.string.search_word_in_english_title)
    }

    private fun showAddDialog() {
        AddWordDialog().apply {
            setActionListener(object : AddWordDialog.AddButtonClickListener {
                override fun onClickWordAdd(wordText: String, wordTranslation: String) {
                    viewModel.addWordToVocabulary(
                        VocabularyWordUI(
                            UUID.randomUUID().toString(),
                            wordText,
                            wordTranslation
                        )
                    )
                }
            })
        }.show(requireActivity().supportFragmentManager, "AddWordDialogFragmentTag")
    }

    private fun showWordOfADay(minicard: WordMinicardUI) {
        binding.wordOfADayBlock.apply {
            wordTitle = minicard.title
            wordTranscription = minicard.transcription
            setTranslationsItems(minicard.translation)
            setOnPlayButtonClickListener { viewModel.onPlaySoundButtonCLick(minicard) }
            setOnFavoriteIconClickListener(
                viewModel::addWordToVocabulary,
                viewModel::removeFromVocabulary
            )
        }
    }
}

