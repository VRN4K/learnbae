package com.learnbae.my.di

import com.github.terrakok.cicerone.Cicerone
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val ciceroneModule = module {
    val cicerone = Cicerone.create()
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}

val interactorModule = module {
    single { androidContext().resources }
}

val dataBaseModule = module {
//    single { RetrofitInstance.getInstance().create(::class.java) }
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
