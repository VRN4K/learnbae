package com.learnbae.my.di

import android.content.Context
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.learnbae.my.data.net.repository.AuthRepository
import com.learnbae.my.data.net.repository.TranslationNetRepository
import com.learnbae.my.data.net.repository.VocabularyNetRepository
import com.learnbae.my.data.net.retrofit.RetrofitInstance
import com.learnbae.my.data.net.retrofit.TranslationService
import com.learnbae.my.data.net.retrofit.VocabularyInterceptor
import com.learnbae.my.data.net.retrofit.VocabularyInterceptor.Companion.authKeyPair
import com.learnbae.my.data.net.retrofit.VocabularyInterceptor.Companion.authKeyStore
import com.learnbae.my.data.net.retrofit.VocabularyService
import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.preferences.AuthorizationPreferenceRepository
import com.learnbae.my.data.storage.preferences.StringPreference
import com.learnbae.my.data.storage.preferences.TokenPreference
import com.learnbae.my.data.storage.repositories.UserDBRepository
import com.learnbae.my.data.storage.repositories.VocabularyDBRepository
import com.learnbae.my.domain.datacontracts.interfaces.*
import com.learnbae.my.domain.interactors.TranslationInteractor
import com.learnbae.my.domain.interactors.UserInteractor
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import com.learnbae.my.domain.interfaces.IUserInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import kotlin.math.sin

val ciceroneModule = module {
    val cicerone = Cicerone.create()
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}

val firebaseModule = module {
    single { Firebase.auth }
    single { Firebase.database }

    single {
        TokenPreference(
            androidContext().getSharedPreferences(
                "USER_AUTH_KEY_STORE",
                Context.MODE_PRIVATE
            )
        )
    }
    single<IAuthorizationStorageRepository> { AuthorizationPreferenceRepository(get()) }
    single<IAuthRepository> { AuthRepository(get()) }
    single<IUserDBRepository> { UserDBRepository(get()) }
    single<IUserInteractor> { UserInteractor(get(), get(), get()) }
}

val interactorModule = module {
    single { androidContext().resources }
    single<ITranslationNetRepository> { TranslationNetRepository() }
    single<IVocabularyDBRepository> { VocabularyDBRepository() }
    single<IVocabularyNetRepository> { VocabularyNetRepository() }
    single<ITranslationInteractor> { TranslationInteractor(get(), get(), get(), get()) }

    single(named("DefaultClient")) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }
    single(named("TranslatorClient")) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor(VocabularyInterceptor()).build()
    }
    single(named("VocabularyClient")) {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }
    single {
        StringPreference(
            androidContext().getSharedPreferences(
                authKeyStore,
                Context.MODE_PRIVATE
            ), authKeyPair
        )
    }

    single<TranslationService> {
        RetrofitInstance.retrofitTranslator.create(TranslationService::class.java)
    }
    single<VocabularyService> {
        RetrofitInstance.retrofitVocabulary.create(VocabularyService::class.java)
    }
}

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            VocabularyDataBase::class.java,
            "learnbae-database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
