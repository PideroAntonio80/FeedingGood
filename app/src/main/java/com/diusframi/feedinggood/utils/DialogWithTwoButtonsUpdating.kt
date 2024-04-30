package com.diusframi.feedinggood.utils

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diusframi.feedinggood.data.localdb.database.FeedingGoodDatabase
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.DialogWithTwoButtonsUpdatingBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DialogWithTwoButtonsUpdating(
    private val foodEntity: FoodEntity,
    private val option: Int,
    private val onButtonYesClick: (() -> Unit)? = null,
    private val onButtonNoClick: (() -> Unit)? = null
) : BaseDialog<DialogWithTwoButtonsUpdatingBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return DialogWithTwoButtonsUpdatingBinding.inflate(inflater, container, false).also {
            _binding = it
        }.root
    }

    override fun setView() {}

    @SuppressLint("ClickableViewAccessibility")
    override fun setListeners() {

        binding.apply {

            dialogWithButtonYes.setOnClickListener {

                if (etName.text?.isEmpty() == false) {

                    updateDataInDb(etName.text.toString(), foodEntity, option)

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
        }
    }

    private fun updateDataInDb(data: String, foodEntity: FoodEntity, option: Int) {

        val myCoroutine = CoroutineScope(Dispatchers.IO)

        myCoroutine.launch(Dispatchers.IO) {
            val updatedFoodEntity = FeedingGoodDatabase.database.getFoodDao().getById(foodEntity.id)

            when(option) {
                1 -> updatedFoodEntity?.calories = data
                2 -> updatedFoodEntity?.carbohydrates = data
                3 -> updatedFoodEntity?.fat = data
                4 -> updatedFoodEntity?.proteins = data
            }

            updatedFoodEntity?.let {
                FeedingGoodDatabase.database.getFoodDao().update(it)
            }
        }
    }
}