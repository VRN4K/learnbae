package com.learnbae.my.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.learnbae.my.data.net.repository.AuthRepository
import com.learnbae.my.data.net.repository.FirebaseStorageRepository
import com.learnbae.my.data.storage.preferences.AuthorizationPreferenceRepository
import com.learnbae.my.data.storage.preferences.TokenPreference
import com.learnbae.my.data.storage.repositories.UserDBRepository
import com.learnbae.my.data.storage.repositories.VocabularyFirebaseRepository
import com.learnbae.my.domain.datacontracts.interfaces.*
import com.learnbae.my.domain.interactors.UserInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = Firebase.database

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = Firebase.storage

    @Provides
    @Singleton

    fun provideTokenPreference(@ApplicationContext context: Context): TokenPreference {
        return TokenPreference(
            context.getSharedPreferences(
                "USER_AUTH_KEY_STORE",
                Context.MODE_PRIVATE
            )
        )
    }

    @Provides
    @Singleton
    fun provideAuthorizationStorageRepository(tokenPreference: TokenPreference): IAuthorizationStorageRepository {
        return AuthorizationPreferenceRepository(tokenPreference)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): IAuthRepository {
        return AuthRepository(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideUserDBRepository(firebaseDatabase: FirebaseDatabase): IUserDBRepository {
        return UserDBRepository(firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideStorageRepository(firebaseStorage: FirebaseStorage,@ApplicationContext context: Context): IStorageRepository {
        return FirebaseStorageRepository(firebaseStorage,context)
    }

    @Provides
    @Singleton
    fun provideVocabularyFirebaseRepository(firebaseDatabase: FirebaseDatabase): IVocabularyFirebaseRepository {
        return VocabularyFirebaseRepository(firebaseDatabase)
    }


    @Provides
    @Singleton
    fun provideUserInteractor(
        authRepository: IAuthRepository,
        authorizationPreferenceRepository: IAuthorizationStorageRepository,
        storageRepository: IStorageRepository,
        userDBRepository: IUserDBRepository,
        @ApplicationContext context: Context
    ): IUserInteractor {
        return UserInteractor(
            authRepository,
            authorizationPreferenceRepository,
            storageRepository,
            userDBRepository,
            context
        )
    }
}