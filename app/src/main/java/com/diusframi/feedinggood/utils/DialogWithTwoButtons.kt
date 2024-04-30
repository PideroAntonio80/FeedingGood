package com.diusframi.feedinggood.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.DialogWithTwoButtonsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogWithTwoButtons(
    private val onButtonYesClick: (() -> Unit)? = null,
    private val onButtonNoClick: (() -> Unit)? = null
) : BaseDialog<DialogWithTwoButtonsBinding>() {

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

                if (etName.text?.isEmpty() == false &&
                    etType.text?.isEmpty() == false &&
                    etCalories.text?.isEmpty() == false &&
                    etCarbohydrates.text?.isEmpty() == false &&
                    etFat.text?.isEmpty() == false &&
                    etProteins.text?.isEmpty() == false) {

                    val newFoodEntity = FoodEntity(
                        name = etName.text.toString(),
                        type = etType.text.toString(),
                        isVegetable = cbIsVegetable.isChecked,
                        calories = etCalories.text.toString(),
                        carbohydrates = etCarbohydrates.text.toString(),
                        fat = etFat.text.toString(),
                        proteins = etProteins.text.toString(),
                        date = System.currentTimeMillis())

                    insertInDb(newFoodEntity)

                    onButtonYesClick?.invoke()
                }
                else {
                    tvError.visibility = View.VISIBLE
                }
            }

            dialogWithButtonNo.setOnClickListener {
                onButtonNoClick?.invoke()
            }

            etName.setOnTouchListener { view, motionEvent ->
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

    private fun insertInDb(foodEntity: FoodEntity) {

        val myCoroutine = CoroutineScope(Dispatchers.IO)

        myCoroutine.launch(Dispatchers.IO) {
            FeedingGoodDatabase.database.getDao().insert(foodEntity)
        }
    }
}