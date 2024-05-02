package com.diusframi.feedinggood.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.diusframi.feedinggood.MainActivity
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        Handler(Looper.myLooper()!!).postDelayed({
            findNavController().navigate(R.id.loginFragment)
        }, 2000)

        return binding.root
    }
}