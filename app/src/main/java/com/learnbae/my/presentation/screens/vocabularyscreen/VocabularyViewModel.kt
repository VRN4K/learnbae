package com.learnbae.my.presentation.screens.vocabularyscreen

import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class VocabularyViewModel : BaseViewModel() {
    private val translationInteractor: ITranslationInteractor by inject()
    val vocabulary = StateLiveData<List<VocabularyWordUI>>()

    init {
        vocabulary.postLoading()
        launchIO { getAllVocabularyWords()}
    }

    fun removeWordFromVocabulary(wordUI: VocabularyWordUI) {
        launchIO { translationInteractor.deleteWordById(wordUI.id) }
    }

    private suspend fun getAllVocabularyWords() {
        vocabulary.postComplete(translationInteractor.getAllWords())
    }

}