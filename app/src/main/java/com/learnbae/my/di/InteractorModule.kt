package com.learnbae.my.di

import android.content.Context
import android.content.res.Resources
import com.learnbae.my.data.net.repository.TranslationNetRepository
import com.learnbae.my.data.net.retrofit.*
import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.repositories.VocabularyDBRepository
import com.learnbae.my.domain.datacontracts.interfaces.*
import com.learnbae.my.domain.interactors.TranslationInteractor
import com.learnbae.my.domain.interfaces.ITranslationInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class InteractorModule {

    @Provides
    @Singleton
    fun provideTranslationNetRepository(service: DictionaryService): ITranslationNetRepository {
        return TranslationNetRepository(service)
    }

    @Provides
    @Singleton
    fun provideResources(@ApplicationContext context: Context): Resources {
        return context.resources
    }

    @Provides
    @Singleton
    fun provideVocabularyDBRepository(dbVocabulary: VocabularyDataBase): IVocabularyDBRepository {
        return VocabularyDBRepository(dbVocabulary)
    }

    @Provides
    @Singleton
    fun provideTranslationInteractor(
        translationNetRepository: ITranslationNetRepository,
        vocabularyDBRepository: IVocabularyDBRepository,
        vocabularyFirebaseRepository: IVocabularyFirebaseRepository,
        @ApplicationContext context: Context
    ): ITranslationInteractor {
        return TranslationInteractor(
            translationNetRepository,
            vocabularyDBRepository,
            vocabularyFirebaseRepository,
            context.resources
        )
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DictionaryClient

    @Provides
    @Singleton
    @DefaultClient
    fun provideDefaultClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }

    @Provides
    @Singleton
    @DictionaryClient
    fun provideDictionaryClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor(DictionaryInterceptor()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        @DictionaryClient translatorClient: OkHttpClient,
    ): RetrofitInstance =
        RetrofitInstance(translatorClient)

    @Provides
    @Singleton
    fun provideRetrofitDictionaryService(retrofitInstance: RetrofitInstance): DictionaryService {
        return retrofitInstance.retrofitTranslator.create(DictionaryService::class.java)
    }
}