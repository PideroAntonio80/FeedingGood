package com.diusframi.feedinggood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.FragmentFoodDetailBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import com.diusframi.feedinggood.utils.MY_FOOD_DETAIL_KEY
import java.text.SimpleDateFormat
import java.util.Locale

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentFoodEntity: FoodEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

            currentFoodEntity = it.getSerializable(MY_FOOD_DETAIL_KEY) as FoodEntity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
//        initListeners()
    }

    private fun initView() {
        binding.apply {
            tvId.text = currentFoodEntity.id.toString()
            tvName.text = currentFoodEntity.name
            tvType.text = currentFoodEntity.type

            if (currentFoodEntity.isVegetable) {
                tvIsVegetable.text = "Vegetal"
            } else {
                tvIsVegetable.text = "No Vegetal"
            }

            tvCalories.text = currentFoodEntity.calories
            tvCarbohydrates.text = currentFoodEntity.carbohydrates
            tvFat.text = currentFoodEntity.fat
            tvProteins.text = currentFoodEntity.proteins

            val myDate = currentFoodEntity.date
            val notificationDate = SimpleDateFormat(DATE_FORMAT_FOOD, Locale.getDefault()).format(myDate)
            tvDate.text = notificationDate
        }
    }

//    private fun initListeners() {
//
//    }
}