package com.learnbae.my.domain.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.learnbae.my.MainActivityViewModel.Companion.DATA_KEY
import com.learnbae.my.domain.datacontracts.model.ActionType
import com.learnbae.my.domain.datacontracts.model.IntentDataModel
import com.learnbae.my.presentation.screens.Screens
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ActionLauncher @Inject constructor(
    @ApplicationContext private val context: Context,
    private val router: Router
) : CoroutineScope {
    override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()

    private fun fromJson(value: String) = Gson().fromJson(value, IntentDataModel::class.java)

    fun action(intent: Intent) {
        val intentData = fromJson(intent.getStringExtra(DATA_KEY)!!)

        when (intentData.actionType) {
            ActionType.RESET_CODE -> router.navigateTo(Screens.getResetPasswordFragment(intentData.intentData))
            ActionType.SHARE -> TODO()
        }
    }
}