package com.learnbae.my.presentation.screens.mainscreen

import androidx.lifecycle.MutableLiveData
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import ltst.nibirualert.my.domain.launchIO
import ltst.nibirualert.my.domain.withIO
import org.koin.core.component.inject

class MainScreenViewModel : BaseViewModel() {
    companion object {
        private const val LAST_ADDED_WORDS_COUNT = 5
    }

    private val translationInteractor: ITranslationInteractor by inject()

    val wordOfADay = StateLiveData<WordMinicardUI>()
    val vocabulary = StateLiveData<List<VocabularyWordUI>>()
    val countTitle = MutableLiveData<Int>()

    init {
        wordOfADay.postLoading()
        launchIO {
            getLastFiveWords()
            wordOfADay.postComplete(translationInteractor.getWordMinicard("tree"))
        }
    }

    fun addWordToVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.addWordToVocabulary(wordUI)
            getLastFiveWords()
        }
    }

    private suspend fun getLastFiveWords() {
        translationInteractor.getAllWords().apply {
            if (this.isNotEmpty()) {
                countTitle.postValue(this.size)
                vocabulary.postComplete(this.take(LAST_ADDED_WORDS_COUNT))
            } else {
                vocabulary.postEmpty()
            }
        }
    }
}