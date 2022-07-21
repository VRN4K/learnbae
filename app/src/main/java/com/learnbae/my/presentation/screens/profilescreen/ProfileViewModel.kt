package com.learnbae.my.presentation.screens.profilescreen

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.model.UserProfileInfoUIModel
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import com.learnbae.my.presentation.base.BaseViewModel
import com.learnbae.my.presentation.common.exceptions.createExceptionHandler
import com.learnbae.my.presentation.common.livedata.StateLiveData
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import ltst.nibirualert.my.domain.launchIO
import ltst.nibirualert.my.domain.withUI
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val userInteractor: IUserInteractor,
    private val translationInteractor: ITranslationInteractor,
    private val vocabularyFirebaseRepository: IVocabularyFirebaseRepository
) : BaseViewModel() {
    val userInformation = StateLiveData<UserProfileInfoUIModel>()
    val isSynchronizing = MutableLiveData<Boolean>()

    init {
        userInformation.postLoading()
    }

    fun isUserAuthorizedCheck() {
        launchIO(createExceptionHandler {
            onException(it)
        }) {
            if (userInteractor.isUserAuthorized()) {
                userInteractor.getUserId()?.let {
                    userInformation.postComplete(
                        userInteractor.getUserInfo(
                            vocabularyFirebaseRepository.getWordsCount(
                                it
                            )
                        )
                    )
                } ?: userInteractor.logout().also { isUserAuthorizedCheck() }
            } else {
                withUI { openFragment(Screens.getAuthScreen()) }
            }
        }
    }

    fun synchronizeWords() {
        isSynchronizing.postValue(true)
        launchIO {
            translationInteractor.synchronizeWords(userInteractor.getUserId()!!)
            isSynchronizing.postValue(false)
        }
    }

    fun addUserProfilePhoto(uri: Uri) {
        launchIO { userInteractor.uploadUserProfilePhoto(uri) }
    }

    fun updateEnglishLevel(englishLevel: String) {
        launchIO { userInteractor.updateEnglishLevel(englishLevel) }
    }

    fun deleteAccount() {
        userInformation.postLoading()
        launchIO {
            userInteractor.deleteAccount()?.let {
                translationInteractor.deleteAllWordsFromAccount(it)
            }
            openFragment(Screens.getAuthScreen())
        }
    }
}