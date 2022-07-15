package com.learnbae.my.di

import android.content.Context
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.learnbae.my.domain.utils.ActionLauncher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object CiceroneModule {
    @Provides
    @Singleton
    fun getCicerone(): Cicerone<Router> = Cicerone.create()

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<Router>): Router = cicerone.router

    @Provides
    @Singleton
    fun provideNavHolder(cicerone: Cicerone<Router>): NavigatorHolder =
        cicerone.getNavigatorHolder()

    @Provides
    @Singleton
    fun provideActionLauncher(
        @ApplicationContext context: Context,
        router: Router
    ): ActionLauncher = ActionLauncher(context, router)
}
