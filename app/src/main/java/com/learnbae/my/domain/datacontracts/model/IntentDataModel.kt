package com.learnbae.my.domain.datacontracts.model

data class IntentDataModel(
    val actionType: ActionType,
    val intentData: String
)

enum class ActionType {
    RESET_CODE, SHARE
}