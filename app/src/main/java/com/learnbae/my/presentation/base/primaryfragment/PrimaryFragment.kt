package com.learnbae.my.presentation.base.primaryfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.learnbae.my.R
import com.learnbae.my.databinding.PrimaryLayoutBinding
import com.learnbae.my.presentation.base.BaseFragment
import com.learnbae.my.presentation.common.setVisibility
import com.learnbae.my.presentation.screens.Screens
import ltst.nibirualert.my.presentation.common.onDestroyNullable

class PrimaryFragment : BaseFragment() {
    private var binding by onDestroyNullable<PrimaryLayoutBinding>()
    private val viewModel by viewModels<PrimaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = PrimaryLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setListeners()
        viewModel.openRootScreen()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setListeners() {
        binding.bottomNavigationView.menu.forEach { menuItem ->
            NavBarItems.values().onEach { navBarItem ->
                if (menuItem.itemId == navBarItem.menuId) menuItem.setOnMenuItemClickListener {
                    viewModel.openFragment(
                        navBarItem.screen
                    )
                    false
                }
            }
        }
    }

    override fun setNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigationView.setVisibility(isVisible)
    }
}

enum class NavBarItems(val menuId: Int, val screen: FragmentScreen) {
    HOME(R.id.navigation_item_main, Screens.getMainScreen()),
    VOCABULARY(R.id.navigation_item_vocabulary, Screens.getVocabularyScreen()),
    PROFILE(R.id.navigation_item_profile, Screens.getProfileScreen())
}