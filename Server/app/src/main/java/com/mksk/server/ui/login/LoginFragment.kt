package com.mksk.server.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mksk.server.R
import com.mksk.server.databinding.FragmentLoginBinding
import com.mksk.server.utils.Constants.PASSWORD_DEFAULT

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnLogin.setOnClickListener {
                val pass = edtPass.editText?.text.toString().trim()
//                val pass = PASSWORD_DEFAULT

                if (pass == PASSWORD_DEFAULT) {
                    val action = LoginFragmentDirections.actionLoginFragmentToMainManagerFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.mkkd), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}