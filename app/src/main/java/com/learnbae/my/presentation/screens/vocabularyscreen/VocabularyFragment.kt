package com.learnbae.my.presentation.screens.vocabularyscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learnbae.my.databinding.VocabularyItemBinding
import com.learnbae.my.databinding.VocabularyScreenBinding
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.presentation.common.livedata.StateData
import com.learnbae.my.presentation.common.recycler.SimpleAdapter
import com.learnbae.my.presentation.screens.vocabularyscreen.holder.VocabularyHolder
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class VocabularyFragment : Fragment() {
    private var binding by onDestroyNullable<VocabularyScreenBinding>()
    private val vocabularyViewModel by lazy { ViewModelProvider(this).get(VocabularyViewModel::class.java) }

    private val wordsListAdapter by lazy {
        SimpleAdapter(
            VocabularyItemBinding::inflate,
            createViewHolder = { VocabularyHolder(it) },
            onClickCallback = { item, pos ->
                removeItemFromScreen(item, pos)
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = VocabularyScreenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vocabularyWordsRecycler.adapter = wordsListAdapter
        setObservers()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingAnimator.changeLoadingState(isLoading, binding.vocabularyWordsRecycler.id)
    }

    private fun removeItemFromScreen(wordUI: VocabularyWordUI, pos: Int) {
        wordsListAdapter.removeItemByIndex(pos)
        vocabularyViewModel.removeWordFromVocabulary(wordUI)
    }

    private fun setObservers() {
        vocabularyViewModel.apply {
            vocabulary.observe(viewLifecycleOwner) {
                when (it.status) {
                    StateData.DataStatus.LOADING -> showLoading(true)
                    StateData.DataStatus.COMPLETE -> with(it.data) {
                        wordsListAdapter.swapItems(this!!)
                        showLoading(false)
                    }
                    else -> return@observe
                }
            }
        }
    }
}