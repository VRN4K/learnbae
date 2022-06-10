package com.learnbae.my.presentation.screens.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learnbae.my.data.model.WordMinicardModel
import com.learnbae.my.databinding.MainScreenBinding
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.presentation.common.livedata.StateData
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class MainScreenFragment : Fragment() {
    private var binding by onDestroyNullable<MainScreenBinding>()
    private val mainScreenViewModel by lazy { ViewModelProvider(this).get(MainScreenViewModel::class.java) }

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
        setObservers()
    }

    private fun setObservers() {
        mainScreenViewModel.wordOfADay.observe(viewLifecycleOwner) {
            when (it.status) {
                StateData.DataStatus.LOADING -> showLoading(true)
                StateData.DataStatus.COMPLETE -> with(it.data) {
                    showLoading(false)
                    showWordOfADay(this!!)
                }
                else -> return@observe
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.wordOfADayBlock.changeLoadingState(isLoading)
    }

    private fun showWordOfADay(minicard: WordMinicardUI) {
        binding.wordOfADayBlock.apply {
            wordTitle = minicard.title
            wordTranscription = minicard.transcription
            setTranslationsItems(minicard.translation)
        }
    }
}