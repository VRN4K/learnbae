package com.learnbae.my.di

import android.content.Context
import androidx.room.Room
import com.github.terrakok.cicerone.Cicerone
import com.learnbae.my.MainActivityViewModel.Companion.AUTH_KEY
import com.learnbae.my.MainActivityViewModel.Companion.authKeyPair
import com.learnbae.my.MainActivityViewModel.Companion.authKeyStore
import com.learnbae.my.data.net.repository.TranslationNetRepository
import com.learnbae.my.data.net.retrofit.RetrofitInstance
import com.learnbae.my.data.net.retrofit.TranslationService
import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.preferences.StringPreference
import com.learnbae.my.data.storage.repositories.VocabularyDBRepository
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.datacontracts.interfaces.IVocabularyDBRepository
import com.learnbae.my.domain.interactors.TranslationInteractor
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ciceroneModule = module {
    val cicerone = Cicerone.create()
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}

val interactorModule = module {
    single { androidContext().resources }
    single<ITranslationNetRepository> { TranslationNetRepository() }
    single<IVocabularyDBRepository> { VocabularyDBRepository() }
    single<ITranslationInteractor> { TranslationInteractor(get(), get(), get()) }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "Authorization",
                        "Bearer ZXlKaGJHY2lPaUpJVXpJMU5pSXNJblI1Y0NJNklrcFhWQ0o5LmV5SmxlSEFpT2pFMk5UVTNPVFl4TWpRc0lrMXZaR1ZzSWpwN0lrTm9ZWEpoWTNSbGNuTlFaWEpFWVhraU9qVXdNREF3TENKVmMyVnlTV1FpT2pZMk1qWXNJbFZ1YVhGMVpVbGtJam9pTnpGbE1URXpNbVV0Tm1GaE55MDBNbUV6TFdFME1Ea3RNV1kxTlRJd1l6UmhabUZtSW4xOS4tWkYxbjZwaHZqTzg1MjZ4eHlwNFpJQ1JUU3k0NTlrYlBYaDZDWUszTnk4"
                    )
                    .build()
                chain.proceed(request)
            }.build()
    }
    single {
        StringPreference(
            androidContext().getSharedPreferences(
                authKeyStore,
                Context.MODE_PRIVATE
            ), authKeyPair
        )
    }
}

val dataBaseModule = module {
    single<TranslationService> {
        RetrofitInstance.getInstance().create(TranslationService::class.java)
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            VocabularyDataBase::class.java,
            "learnbae-database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}
