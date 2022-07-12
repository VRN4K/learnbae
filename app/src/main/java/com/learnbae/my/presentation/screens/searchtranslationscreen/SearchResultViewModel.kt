package com.learnbae.my.presentation.screens.searchtranslationscreen

import com.learnbae.my.domain.datacontracts.model.SearchResultUIModel
import com.learnbae.my.domain.datacontracts.model.toVocabularyEntity
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.common.livedata.StateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val translationInteractor: ITranslationInteractor,
    private val userInteractor: IUserInteractor
) : BaseViewModel() {
    val wordTranslation = StateLiveData<SearchResultUIModel>()

    init {
        wordTranslation.postLoading()
    }

    fun searchWordTranslation(sourceLang: String, targetLang: String, word: String) {
        launchIO(createExceptionHandler {
            onException(it)
            wordTranslation.postError(it)
        }) {
            wordTranslation.postComplete(
                translationInteractor.getWordTranslation(
                    sourceLang,
                    targetLang,
                    word
                )
            )
        }
    }

    fun addWordToVocabulary() {
        launchIO {
            translationInteractor.addWordToVocabulary(
                userInteractor.getUserId(),
                wordTranslation.value!!.data!!.toVocabularyEntity()
            )
        }
    }
}
