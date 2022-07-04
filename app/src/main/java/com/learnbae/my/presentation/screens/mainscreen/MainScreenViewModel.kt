package com.learnbae.my.presentation.screens.mainscreen

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.learnbae.my.domain.datacontracts.model.VocabularyWordUI
import com.learnbae.my.domain.datacontracts.model.WordMinicardUI
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.livedata.StateLiveData
import kotlinx.coroutines.CoroutineExceptionHandler
import ltst.nibirualert.my.domain.launchIO
import org.koin.core.component.inject
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
class MainScreenViewModel : BaseViewModel() {
    companion object {
        private const val LAST_ADDED_WORDS_COUNT = 5
    }

    private val translationInteractor: ITranslationInteractor by inject()
    private val userInteractor: IUserInteractor by inject()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    val wordOfADay = StateLiveData<WordMinicardUI>()
    val vocabulary = StateLiveData<List<VocabularyWordUI>>()
    val mediaSourceData = MutableLiveData<MediaItem>()
    val countTitle = MutableLiveData<Int>()

    init {
        val handler = CoroutineExceptionHandler { _, exception ->
            exception.printStackTrace()
            wordOfADay.postLoading()
        }

        wordOfADay.postLoading()
        launchIO(handler) {
            getLastFiveWords()
            //dateFormat.format(Calendar.getInstance().time
            wordOfADay.postComplete(translationInteractor.getWordOfADay("2022-06-21"))
        }
    }

    fun addWordToVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.addWordToVocabulary(userInteractor.getUserId(), wordUI)
            getLastFiveWords()
        }
    }

    fun onPlaySoundButtonCLick(minicard: WordMinicardUI) {
        println(minicard.soundURL)
        mediaSourceData.postValue(MediaItem.fromUri(minicard.soundURL!!))
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