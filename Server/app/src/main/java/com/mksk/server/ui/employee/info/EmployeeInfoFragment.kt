package com.mksk.server.ui.employee.info

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mksk.server.R
import com.mksk.server.data.models.Employee
import com.mksk.server.databinding.FragmentEmployeeInfoBinding
import com.mksk.server.ui.employee.EmployeeViewModel

class EmployeeInfoFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeInfoBinding
    private lateinit var menuHost: MenuHost
    private val viewModel: EmployeeViewModel by viewModels()
    private lateinit var employee: Employee
    private val args: EmployeeInfoFragmentArgs by navArgs()
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.employees_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.save) {
                binding.apply {
                    val result = employee.copy(
                        name = edtName.text.toString(),
                        address = edtAddress.text.toString(),
                        password = textInputLayoutPass.editText?.text.toString(),
                        phoneNumber = edtNumberPhone.text.toString()
                    )
                    viewModel.updateEmployee(result)
                }
            }
            findNavController().popBackStack()

            return true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeInfoBinding.inflate(layoutInflater)
        showData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuHost = requireActivity()
        menuHost.addMenuProvider(menuProvider)
    }

    private fun showData() {
        employee = args.employee
        binding.apply {
            edtName.setText(args.employee.name)
            edtAddress.setText(args.employee.address)
            edtNumberPhone.setText(args.employee.phoneNumber)
            textInputLayoutPass.editText?.setText(args.employee.password)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        menuHost.removeMenuProvider(menuProvider)
    }
}