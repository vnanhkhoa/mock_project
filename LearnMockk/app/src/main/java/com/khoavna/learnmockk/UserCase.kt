package com.khoavna.learnmockk

import android.content.Context

class UserCase(private val context: Context) {

    fun method(): String {
        val p = Preference(context)
        if (p.logic) {
            return "khoa"
        }
        return "abc"
    }
}