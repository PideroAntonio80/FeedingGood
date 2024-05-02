package com.diusframi.feedinggood.ui.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.diusframi.feedinggood.FeedingGoodApplication.Companion.preferences
import com.diusframi.feedinggood.MainActivity
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.databinding.FragmentLoginBinding
import com.diusframi.feedinggood.utils.DialogRegister
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private var modalRegister: DialogRegister? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar!!.hide()

        if (preferences.getKeepSession()) {
            findNavController().navigate(R.id.navigation_food_list_fragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as MainActivity).supportActionBar!!.hide()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initListeners() {
        binding.apply {

            loginButton.setOnClickListener {
                if (!etUser.editText?.text.isNullOrEmpty() && !etPassword.editText?.text.isNullOrEmpty()) {

                    doLogin(etUser.editText?.text.toString(), etPassword.editText?.text.toString())
                }
                else {

                    if (etUser.editText?.text.isNullOrEmpty() && etPassword.editText?.text.isNullOrEmpty()) {
                        etUser.error = resources.getString(R.string.login_empty_user)
                        etPassword.error = resources.getString(R.string.login_empty_password)
                    }
                    else if (etUser.editText?.text.isNullOrEmpty()) {
                        etUser.error = resources.getString(R.string.login_empty_user)
                    }
                    else {
                        etPassword.error = resources.getString(R.string.login_empty_password)
                    }
                }
            }

            registerButton.setOnClickListener {

                clearFields()
                clearErrors()

                showModalTwoButtons()
            }

            etUser.editText?.setOnTouchListener { view, motionEvent ->
                clearErrors()
                false
            }

            etPassword.editText?.setOnTouchListener { view, motionEvent ->
                clearErrors()
                false
            }
        }
    }

    private fun doLogin(user: String, password: String) {
        lifecycleScope.launch(Dispatchers.IO) {

            val deferred = async { viewModel.getUserVM(user, password) }
            val response = deferred.await()

            if (response != null) {
                withContext(Dispatchers.Main) {

                    preferences.saveUserName(response.userName)
                    preferences.saveUserPass(response.password)
                    preferences.saveUserDate(response.date)

                    if (binding.cbKeepSession.isChecked) {
                        preferences.saveKeepSession(true)
                    } else {
                        preferences.saveKeepSession(false)
                    }

                    clearErrors()
                    clearFields()

                    findNavController().navigate(R.id.navigation_food_list_fragment)
                }
            }
            else {
                withContext(Dispatchers.Main) {
                    binding.etUser.error = resources.getString(R.string.login_unknown_user_error)
                    binding.etPassword.error = resources.getString(R.string.login_unknown_user_error)
                }
            }
        }
    }

    private fun showModalTwoButtons() {

        modalRegister = DialogRegister()
        modalRegister?.show(this.childFragmentManager, modalRegister!!.tag)
    }

    private fun clearErrors() {
        binding.etUser.error = null
        binding.etPassword.error = null
    }

    private fun clearFields() {
        binding.etUser.editText?.text?.clear()
        binding.etPassword.editText?.text?.clear()
        binding.cbKeepSession.isChecked = false
    }
}