package com.diusframi.feedinggood.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity
import com.diusframi.feedinggood.databinding.DialogRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DialogRegister : BaseDialog<DialogRegisterBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DialogRegisterBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun setView() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {

        binding.apply {

            registerButton.setOnClickListener {

                registerButton.isEnabled = false

                if (etName.text?.isEmpty() == false && etPass.text?.isEmpty() == false) {

                    val newUserLoginEntity = UserLoginEntity(
                        userName = etName.text.toString().trim(),
                        password = etPass.text.toString(),
                        date = System.currentTimeMillis())

                    lifecycleScope.launch(Dispatchers.IO) {

                        val def = async { FeedingGoodDatabase.database.getUserLoginDao().getByUserName(newUserLoginEntity.userName) }

                        val response = def.await()

                        if (response == null) {

                            val deferred = async { FeedingGoodDatabase.database.getUserLoginDao().insert(newUserLoginEntity) }
                            deferred.await()

                            withContext(Dispatchers.Main) {
                                makeToast(resources.getString(R.string.login_register_created))
                                registerButton.isEnabled = true
                            }

                            dismiss()
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                registerButton.isEnabled = true

                                tvAlreadyExist.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                else {
                    registerButton.isEnabled = true

                    tvError.visibility = View.VISIBLE
                }
            }

            registerCancel.setOnClickListener {

                registerButton.isEnabled = true

                dismiss()
            }

            etName.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                tvAlreadyExist.visibility = View.GONE
                false
            }

            etPass.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                tvAlreadyExist.visibility = View.GONE
                false
            }
        }
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}