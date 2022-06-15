package com.learnbae.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.learnbae.my.databinding.ActivityMainBinding
import ltst.nibirualert.my.presentation.common.onDestroyNullable
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val mainActivityViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }
    private val navHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHolder.setNavigator(AppNavigator(this, R.id.fragmentContainerView))
        mainActivityViewModel.openRootScreen()
    }
}