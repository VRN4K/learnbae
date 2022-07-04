package com.learnbae.my.presentation.screens.vocabularyscreen

import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject

class VocabularyViewModel : BaseViewModel() {
    private val translationInteractor: ITranslationInteractor by inject()
    private val userInteractor: IUserInteractor by inject()
    val vocabulary = StateLiveData<List<VocabularyWordUI>>()

    init {
        getAllVocabularyWords()
    }

    fun removeWordFromVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.deleteWordById(userInteractor.getUserId(), wordUI.id)
        }
    }

    fun addWordToVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.addWordToVocabulary(userInteractor.getUserId(), wordUI)
            getAllVocabularyWords()
        }
    }

    private fun getAllVocabularyWords() {
        vocabulary.postLoading()
        launchIO {
            vocabulary.postComplete(translationInteractor.getAllWords())
            userInteractor.getUserId()?.let { println("isSynchronize: " + translationInteractor.isWordsSynchronize(it)) }
        }
    }
}