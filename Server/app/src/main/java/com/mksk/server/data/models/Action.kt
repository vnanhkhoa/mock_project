package com.mksk.server.data.models

data class Action(val iconAction: Int, val actionName: String, val action: () -> Unit)