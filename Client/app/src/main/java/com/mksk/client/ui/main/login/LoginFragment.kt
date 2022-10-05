package com.mksk.client.ui.main.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.mksk.client.R
import com.mksk.client.databinding.FragmentLoginBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.server.data.models.Employee

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var binding: FragmentLoginBinding
    private lateinit var userDropDown: MaterialAutoCompleteTextView
    private lateinit var arrayAdapter: ArrayAdapter<Employee>
    private val list = arrayListOf<Employee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            list
        )
        mainViewModel.listEmployees.observe(requireActivity()) {
            list.clear()
            list.addAll(it)
            arrayAdapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login, container, false
        )
        binding.mv = viewModel
        binding.lifecycleOwner = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {

            userDropDown = txtUser.editText as MaterialAutoCompleteTextView

            userDropDown.setAdapter(arrayAdapter)
            userDropDown.onItemClickListener = OnItemClickListener { _, _, position, _ ->
                viewModel.setEmployee(mainViewModel.listEmployees.value!![position])
            }

            btnLogin.setOnClickListener {
                val validate = viewModel.isValidate()
                if (!validate) {
                    Toast.makeText(requireContext(), "Vui lòng chọn User", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                val result = viewModel.handleLogin()
                if (!result) {
                    Toast.makeText(requireContext(), "Sai Mật khẩu", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                mainViewModel.setEmployee(viewModel.getEmployee())
                findNavController().navigate(R.id.action_loginFragment_to_tableFragment)
            }
        }
    }
}