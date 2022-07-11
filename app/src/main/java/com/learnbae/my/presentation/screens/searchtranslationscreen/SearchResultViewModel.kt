package com.learnbae.my.presentation.screens.searchtranslationscreen

import com.learnbae.my.domain.datacontracts.model.SearchResultUIModel
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val translationInteractor: ITranslationInteractor
) : BaseViewModel() {
    val wordTranslation = StateLiveData<SearchResultUIModel>()

    init { wordTranslation.postLoading() }

    fun searchWordTranslation(sourceLang: String, targetLang: String, word: String) {
        launchIO {
            wordTranslation.postComplete(
                translationInteractor.getWordTranslation(
                    sourceLang,
                    targetLang,
                    word
                )
            )
        }
    }
}
