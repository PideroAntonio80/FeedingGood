package com.diusframi.feedinggood.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.DialogWithTwoButtonsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class DialogWithTwoButtons : BaseDialog<DialogWithTwoButtonsBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DialogWithTwoButtonsBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun setView() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {

        binding.apply {

            dialogWithButtonYes.setOnClickListener {

                dialogWithButtonYes.isEnabled = false

                if (etName.text?.isEmpty() == false &&
                    etType.text?.isEmpty() == false &&
                    etCalories.text?.isEmpty() == false &&
                    etCarbohydrates.text?.isEmpty() == false &&
                    etFat.text?.isEmpty() == false &&
                    etProteins.text?.isEmpty() == false) {

                    val newFoodEntity = FoodEntity(
                        name = etName.text.toString().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        type = etType.text.toString(),
                        isVegetable = cbIsVegetable.isChecked,
                        calories = etCalories.text.toString(),
                        carbohydrates = etCarbohydrates.text.toString(),
                        fat = etFat.text.toString(),
                        proteins = etProteins.text.toString(),
                        date = System.currentTimeMillis())

                    lifecycleScope.launch(Dispatchers.IO) {

                        val def = async { FeedingGoodDatabase.database.getFoodDao().getByName(newFoodEntity.name) }

                        val response = def.await()

                        if (response == null) {

                            val deferred = async { FeedingGoodDatabase.database.getFoodDao().insert(newFoodEntity) }
                            deferred.await()

                            withContext(Dispatchers.Main) {
                                dialogWithButtonYes.isEnabled = true
                            }

                            dismiss()
                        }
                        else {
                            withContext(Dispatchers.Main) {
                                dialogWithButtonYes.isEnabled = true

                                tvAlreadyExist.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                else {
                    dialogWithButtonYes.isEnabled = true

                    tvError.visibility = View.VISIBLE
                }
            }

            dialogWithButtonNo.setOnClickListener {
                dialogWithButtonYes.isEnabled = true

                dismiss()
            }

            etName.setOnTouchListener { view, motionEvent ->
                tvAlreadyExist.visibility = View.GONE
                tvError.visibility = View.GONE
                false
            }

            etType.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                false
            }

            etCalories.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                false
            }

            etCarbohydrates.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                false
            }

            etFat.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                false
            }

            etProteins.setOnTouchListener { view, motionEvent ->
                tvError.visibility = View.GONE
                false
            }
        }
    }
}