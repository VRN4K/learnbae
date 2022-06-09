package com.learnbae.my.di

import com.github.terrakok.cicerone.Cicerone
import com.learnbae.my.data.model.Translation
import com.learnbae.my.data.repository.TranslationNetRepository
import com.learnbae.my.data.retrofit.RetrofitInstance
import com.learnbae.my.data.retrofit.TranslationService
import com.learnbae.my.domain.datacontracts.interfaces.ITranslationNetRepository
import com.learnbae.my.domain.interactors.TranslationInteractor
import com.learnbae.my.domain.interfaces.ITranslationInteractor
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
    single<ITranslationInteractor> { TranslationInteractor(get(),get()) }
}

val dataBaseModule = module {
    single<TranslationService> { RetrofitInstance.getInstance().create(TranslationService::class.java) }
//    single {
//        Room.databaseBuilder(
//            androidContext(),
//            AsteroidDataBase::class.java,
//            "database"
//        ).addMigrations(MIGRATION_1_2)
//            .fallbackToDestructiveMigration()
//            .build()
//    }
}
