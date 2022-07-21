package com.learnbae.my.presentation.screens.mainscreen

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.exoplayer2.MediaItem
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.common.livedata.StateLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val translationInteractor: ITranslationInteractor,
    val userInteractor: IUserInteractor
) : BaseViewModel() {
    companion object {
        const val SEARCH_RESULT_KEY = "SEARCH RESULT SCREEN KEY"
        private const val LAST_ADDED_WORDS_COUNT = 5
    }

    val wordOfADay = StateLiveData<WordMinicardUI>()
    val vocabulary = StateLiveData<List<VocabularyWordUI>>()
    val mediaSourceData = MutableLiveData<MediaItem>()
    val countTitle = MutableLiveData<Int>()
    val isWordAlreadyInVocabulary = MutableLiveData<Boolean>()

    init {
        launchIO { getLastFiveWords() }
        wordOfADay.postLoading()
        launchIO(createExceptionHandler {
            wordOfADay.postLoading()
        }) {
            val word = translationInteractor.getWordOfADay()
            isWordAlreadyInVocabulary.postValue(
                translationInteractor.isWordAlreadyInVocabulary(
                    userInteractor.getUserId(),
                    word.title
                )
            )
            wordOfADay.postComplete(word)
        }
    }

    fun addWordToVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.addWordToVocabulary(userInteractor.getUserId(), wordUI)
            getLastFiveWords()
        }
    }

    fun onPlaySoundButtonCLick(minicard: WordMinicardUI) {
        mediaSourceData.postValue(MediaItem.fromUri(minicard.soundURL!!))
    }

    fun removeFromVocabulary(word: String) {
        launchIO {
            translationInteractor.deleteWordByTitle(userInteractor.getUserId(), word)
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


    override fun navigateToScreen(screen: FragmentScreen) {
        router.setResultListener(SEARCH_RESULT_KEY) { data ->
            if (data as Boolean) launchIO { getLastFiveWords() }
        }
        super.navigateToScreen(screen)
    }
}