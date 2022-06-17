package com.learnbae.my

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.forEach
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.databinding.ActivityMainBinding
import com.learnbae.my.presentation.common.ActionLauncher
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.presentation.common.onDestroyNullable
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private var binding by onDestroyNullable<ActivityMainBinding>()
    private val actionLauncher: ActionLauncher by inject()
    private val mainActivityViewModel by lazy { ViewModelProvider(this).get(MainActivityViewModel::class.java) }
    private val navHolder: NavigatorHolder by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHolder.setNavigator(AppNavigator(this, binding.fragmentContainerView.id))
        mainActivityViewModel.openRootScreen()
    }

    override fun onStart() {
        super.onStart()
        setListeners()
    }

    //TODO() add navigation
    private fun setListeners() {
        binding.bottomNavigationView.menu.forEach { menuItem ->
            NavBarItems.values().onEach { navBarItem ->
                if (menuItem.itemId == navBarItem.menuId) menuItem.setOnMenuItemClickListener {
                    mainActivityViewModel.openFragment(
                        navBarItem.screen
                    )
                    true
                }
            }
        }
    }
}

enum class NavBarItems(val menuId: Int, val screen: FragmentScreen) {
    HOME(R.id.navigation_item_main, Screens.getMainScreen()),
    VOCABULARY(R.id.navigation_item_vocabulary, Screens.getMainScreen()),
    PROFILE(R.id.navigation_item_profile, Screens.getProfileScreen()) //TODO() add profile screen
}