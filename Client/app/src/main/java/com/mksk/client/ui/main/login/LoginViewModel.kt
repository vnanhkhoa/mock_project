package com.mksk.client.ui.main.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mksk.server.data.models.Employee

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val employee = MutableLiveData<Employee>()

    val inputPassword = MutableLiveData<String?>()


    fun handleLogin(): Boolean {
        return employee.value!!.password == inputPassword.value
    }

    fun setEmployee(temp: Employee) {
        employee.value = temp
    }

    fun getEmployee(): Employee = employee.value!!

    fun isValidate(): Boolean {
        return employee.value != null && !inputPassword.value.isNullOrEmpty()
    }

}