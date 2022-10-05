package com.mksk.client.ui.main.employee.info

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.mksk.client.R
import com.mksk.client.databinding.FragmentEmployeeInfoBinding
import com.mksk.client.ui.main.MainViewModel
import com.mksk.server.data.models.Employee

class EmployeeInfoFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeInfoBinding
    private lateinit var menuHost: MenuHost
    private lateinit var employee: Employee
    private val mainViewModel: MainViewModel by activityViewModels()
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.employees_menu, menu)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            if (menuItem.itemId == R.id.save) {
                binding.apply {
                    val result = employee.copy(
                        name = edtName.editText?.text.toString(),
                        address = edtAddress.editText?.text.toString(),
                        password = edtPass.editText?.text.toString(),
                        phoneNumber = edtNumberPhone.editText?.text.toString()
                    )
                    mainViewModel.setEmployee(result)
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
        binding = FragmentEmployeeInfoBinding.inflate(inflater, container, false)
        mainViewModel.employee.observe(this.viewLifecycleOwner) {
            employee = it
            binding.apply {
                edtName.editText?.setText(it.name)
                edtPass.editText?.setText(it.password)
                edtNumberPhone.editText?.setText((it.phoneNumber))
                edtAddress.editText?.setText(it.address)
            }
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        menuHost = requireActivity()
        menuHost.addMenuProvider(menuProvider)
    }
    override fun onDestroy() {
        super.onDestroy()
        menuHost.removeMenuProvider(menuProvider)
    }
}