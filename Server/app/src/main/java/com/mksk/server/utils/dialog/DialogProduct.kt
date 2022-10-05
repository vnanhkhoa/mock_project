package com.mksk.server.utils.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.mksk.server.R
import com.mksk.server.data.models.Product
import com.mksk.server.databinding.AddProductDialogBinding
import com.mksk.server.utils.Constants.ACTION_UPDATE
import com.mksk.server.utils.ValidateForm

class DialogProduct(
    private val action: String,
    private val listener: (product: Product) -> Unit
) : DialogFragment() {
    private lateinit var binding: AddProductDialogBinding

    private var product: Product? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = AddProductDialogBinding.inflate(layoutInflater)
        if (action == ACTION_UPDATE) {
            product?.let { setData(it) }
        }
        val validateForm = ValidateForm(requireContext())
        binding.apply {
            btnAdd.apply {
                text = action
                setOnClickListener {
                    validateForm.apply {
                        validateLinkProduct(txt1)
                        validateNameProduct(txt2)
                        validatePrice(txt3)
                        if (isValidate) {
                            listener(
                                Product(
                                    productID = if (action == ACTION_UPDATE) product?.productID else null,
                                    productImage = edtLink.text.toString(),
                                    productPrice = edtPrice.text.toString().toLong(),
                                    productName = edtName.text.toString()
                                )
                            )
                            dismiss()
                        }
                    }
                }
            }
            btnLoad.setOnClickListener {
                validateForm.apply {
                    validateLinkProduct(txt1)
                    if (isValidate) {
                        Glide.with(it)
                            .load(edtLink.text.toString().trim())
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_round_error_24)
                            .into(imgCoffee)
                    }
                }
            }
            btnCancel.setOnClickListener { dismiss() }
        }
        handleClearValidate()
        return MaterialAlertDialogBuilder(requireContext()).setView(binding.root).create()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dialog.cancel()
    }

    private fun clearData() {
        binding.apply {
            edtLink.setText("")
            edtPrice.setText("")
            edtName.setText("")
        }
    }

    private fun setData(product: Product) {
        binding.apply {
            edtLink.setText(product.productImage)
            edtPrice.setText(product.productPrice.toString())
            edtName.setText(product.productName)
            Glide.with(requireActivity())
                .load(product.productPrice)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.ic_round_error_24)
                .into(imgCoffee)
        }
    }

    fun showDialog(childFragment: FragmentManager, product: Product? = null) {
        show(childFragment, action)
        this.product = product
    }

    private fun handleClearValidate() {
        binding.apply {
            txt1.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(txt1)
                }
                hint = displayHtml(hint)
            }
            txt2.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(txt2)
                }
                hint = displayHtml(hint)
            }
            txt3.editText?.apply {
                setOnFocusChangeListener { _, _ ->
                    clearValidate(txt3)
                }
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
            edtName.setText("")
            edtPrice.setText("")
            edtLink.setText("")
        }
    }

    private fun displayHtml(text: CharSequence): Spanned {
        val html = "<span>$text <b><sup><font color='red'>*</font></sup></b></span>"
        return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_OPTION_USE_CSS_COLORS)
    }
}