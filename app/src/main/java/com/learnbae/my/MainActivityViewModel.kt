package com.learnbae.my

import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import com.learnbae.my.domain.datacontracts.model.ActionType
import com.learnbae.my.domain.datacontracts.model.IntentDataModel
import com.learnbae.my.domain.utils.ActionLauncher
import com.learnbae.my.domain.utils.DeeplinkMapper
import com.learnbae.my.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : BaseViewModel() {
    companion object {
        const val DATA_KEY = "Intent data key"
    }

    @Inject
    lateinit var actionLauncher: ActionLauncher

    fun onDeeplinkDataReceive(intent: Intent) {
        if (intent.data != null) {
            Log.d("RESET PASSWORD", "get intent")
            deeplinkNavigation(intent)
        }
    }

    private fun deeplinkNavigation(intent: Intent?) {
        val code = DeeplinkMapper().parseFromLink(intent!!.data.toString())
        Log.d("RESET PASSWORD", "sent intent to action launcher")
        intent.apply {
            putExtra(
                DATA_KEY,
                Gson().toJson(
                    IntentDataModel(
                        ActionType.RESET_CODE,
                        code
                    )
                )
            )
        }
        actionLauncher.action(intent)
    }
}