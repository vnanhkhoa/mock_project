package com.mksk.server.utils

import android.content.Context
import com.google.android.material.textfield.TextInputLayout
import com.mksk.server.R

class ValidateForm(private val context: Context) {
    var isValidate = true
        private set

    fun validateName(textInputLayout: TextInputLayout) {
        val name = textInputLayout.editText?.text.toString()
        if (name == "") {
            textInputLayout.error = context.getString(R.string.name_null)
            isValidate = false
        } else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
    fun validateNumberPhone(textInputLayout: TextInputLayout){
        val numberPhone = textInputLayout.editText?.text.toString()
        if (numberPhone == "") {
            textInputLayout.error = context.getString(R.string.numberPhone_null)
            isValidate = false
        }else if(numberPhone.length>12 || numberPhone.length < 9){
            textInputLayout.error = context.getString(R.string.numberPhone_length)
            isValidate = false
        }else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
    fun validateAddress(textInputLayout: TextInputLayout) {
        val address = textInputLayout.editText?.text.toString()
        if (address == "") {
            textInputLayout.error = context.getString(R.string.address_null)
            isValidate = false
        } else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
    fun validateLinkProduct(textInputLayout: TextInputLayout){
        val link = textInputLayout.editText?.text.toString()
        if(link == ""){
            textInputLayout.error = context.getString(R.string.link_null)
            isValidate = false
        }else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
    fun validateNameProduct(textInputLayout: TextInputLayout){
        val name = textInputLayout.editText?.text.toString()
        if(name == ""){
            textInputLayout.error = context.getString(R.string.nameProduct_null)
            isValidate = false
        }else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
    fun validatePrice(textInputLayout: TextInputLayout){
        val price = textInputLayout.editText?.text.toString()
        if(price == ""){
            textInputLayout.error = context.getString(R.string.price_null)
            isValidate = false
        }else textInputLayout.helperText = context.getString(R.string.valid_success)
    }
}