package com.mksk.client.ui.main.welcome

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mksk.client.R
import com.mksk.client.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeFragment : Fragment() {

    lateinit var binding: FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        val animation: Animation = AnimationUtils.loadAnimation(
            activity?.applicationContext,
            R.anim.blink
        )
        binding.tvAppName.startAnimation(animation)
        CoroutineScope(Dispatchers.Main).launch {
            delay(3000L)
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }
        return binding.root
    }

}