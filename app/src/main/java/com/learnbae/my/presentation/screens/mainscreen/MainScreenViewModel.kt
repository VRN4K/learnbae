package com.learnbae.my.presentation.screens.mainscreen

import androidx.lifecycle.MutableLiveData
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.learnbae.my.data.net.retrofit.RetrofitInstance
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
    val mediaSourceData = MutableLiveData<MediaSource>()
    val countTitle = MutableLiveData<Int>()

    init {
        wordOfADay.postLoading()
        launchIO {
            getLastFiveWords()
            withIO { wordOfADay.postComplete(translationInteractor.getWordMinicard("tree")) }
        }
    }

    fun addWordToVocabulary(wordUI: VocabularyWordUI) {
        launchIO {
            translationInteractor.addWordToVocabulary(wordUI)
            getLastFiveWords()
        }
    }

    fun onPlaySoundButtonCLick() {
        val dataSourceFactory = OkHttpDataSource.Factory(okHttpClient)
        val mediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri("${RetrofitInstance.BASE_URL}Sound?dictionaryName=LingvoUniversal%20(En-Ru)&fileName=mother.wav"))
        mediaSourceData.postValue(mediaSource)
    }

    private suspend fun getLastFiveWords() {
        withIO {
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
}