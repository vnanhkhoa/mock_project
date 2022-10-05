package com.mksk.server.ui.employee.add

import android.os.Bundle
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.fromHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.mksk.server.data.models.Employee
import com.mksk.server.databinding.FragmentEmployeeAddBinding
import com.mksk.server.ui.employee.EmployeeViewModel
import com.mksk.server.utils.Constants.PASSWORD_DEFAULT
import com.mksk.server.utils.ValidateForm

class EmployeeAddFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeAddBinding
    private val viewModel: EmployeeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeAddBinding.inflate(layoutInflater)
        binding.btnAdd.setOnClickListener {
            addEmployee()
        }
        handleClearValidate()
        return binding.root
    }

    private fun addEmployee() {
        val validateForm = ValidateForm(requireContext())
        binding.apply {
            val fullName = textInputLayoutName.editText?.text.toString()
            val numberPhone = textInputLayoutPhone.editText?.text.toString()
            val password = PASSWORD_DEFAULT
            val address = textInputLayoutAddress.editText?.text.toString()

            validateForm.apply {
                validateName(textInputLayoutName)
                validateNumberPhone(textInputLayoutPhone)
                validateAddress(textInputLayoutAddress)

                if (isValidate) {
                    viewModel.addEmployee(
                        Employee(
                            null,
                            name = fullName,
                            phoneNumber = numberPhone,
                            address = address,
                            password = password,
                        )
                    )
                    findNavController().popBackStack()
                }
            }
        }

    }

    private fun handleClearValidate() {
        binding.apply {
            textInputLayoutName.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(textInputLayoutName)
                }
                hint = displayHtml(hint)
            }
            textInputLayoutPhone.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(textInputLayoutPhone)
                }
                hint = displayHtml(hint)
            }
            textInputLayoutAddress.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(textInputLayoutAddress)
                }
                hint = displayHtml(hint)
            }

            textInputLayoutPass.editText?.apply {
                setText(PASSWORD_DEFAULT)
                hint = displayHtml(hint)
            }

        }
    }



    private fun clearValidate(txt: TextInputLayout) {
        txt.apply {
            error = null
            helperText = null
        }
    }

    private fun clearInput() {
        binding.apply {
            textInputLayoutName.editText?.setText("")
            textInputLayoutPhone.editText?.setText("")
            textInputLayoutAddress.editText?.setText("")
        }
    }

    private fun displayHtml(text: CharSequence): Spanned {
        val html = "<span>$text <b><sup><font color='red'>*</font></sup></b></span>"
        return fromHtml(html, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    }
}