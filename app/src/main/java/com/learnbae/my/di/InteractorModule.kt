package com.learnbae.my.di

import android.content.Context
import android.content.res.Resources
import com.learnbae.my.data.net.repository.TranslationNetRepository
import com.learnbae.my.data.net.repository.VocabularyNetRepository
import com.learnbae.my.data.net.retrofit.*
import com.learnbae.my.data.storage.VocabularyDataBase
import com.learnbae.my.data.storage.preferences.StringPreference
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
    fun provideTranslationNetRepository(service: TranslationService): ITranslationNetRepository {
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
    fun provideVocabularyNetRepository(service: VocabularyService): IVocabularyNetRepository {
        return VocabularyNetRepository(service)
    }

    @Provides
    @Singleton
    fun provideTranslationInteractor(
        translationNetRepository: ITranslationNetRepository,
        vocabularyDBRepository: IVocabularyDBRepository,
        vocabularyNetRepository: IVocabularyNetRepository,
        vocabularyFirebaseRepository: IVocabularyFirebaseRepository,
        @ApplicationContext context: Context
    ): ITranslationInteractor {
        return TranslationInteractor(
            translationNetRepository,
            vocabularyDBRepository,
            vocabularyNetRepository,
            vocabularyFirebaseRepository,
            context.resources
        )
    }

    @Provides
    @Singleton
    fun provideStringPreference(@ApplicationContext context: Context): StringPreference {
        return StringPreference(
            context.getSharedPreferences(
                VocabularyInterceptor.authKeyStore,
                Context.MODE_PRIVATE
            ), VocabularyInterceptor.authKeyPair
        )
    }

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class DefaultClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class TranslatorClient

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class VocabularyClient

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
    @TranslatorClient
    fun provideTranslatorClient(sharedPreferences: StringPreference): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            })
            .addInterceptor(VocabularyInterceptor(sharedPreferences)).build()
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
    @VocabularyClient
    fun provideVocabularyClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }).build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        @TranslatorClient translatorClient: OkHttpClient,
        @VocabularyClient vocabularyClient: OkHttpClient,
        @DictionaryClient translatorDictionary: OkHttpClient,
    ): RetrofitInstance =
        RetrofitInstance(translatorClient, vocabularyClient, translatorDictionary)

    @Provides
    @Singleton
    fun provideRetrofitTranslationService(retrofitInstance: RetrofitInstance): TranslationService {
        return retrofitInstance.retrofitNewTranslator.create(TranslationService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitVocabularyService(retrofitInstance: RetrofitInstance): VocabularyService {
        return retrofitInstance.retrofitVocabulary.create(VocabularyService::class.java)
    }
}