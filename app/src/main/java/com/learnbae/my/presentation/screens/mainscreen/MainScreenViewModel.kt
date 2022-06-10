package com.learnbae.my.presentation.screens.mainscreen

import androidx.lifecycle.MutableLiveData
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class MainScreenViewModel : BaseViewModel() {
    private val translationInteractor: ITranslationInteractor by inject()
    val wordOfADay = StateLiveData<WordMinicardUI>()

    init {
        wordOfADay.postLoading()

        launchIO {
            wordOfADay.postComplete(translationInteractor.getWordMinicard("tree"))
        }
    }
}