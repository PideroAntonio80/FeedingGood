package com.diusframi.feedinggood.ui.foodDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.FragmentFoodDetailBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import com.diusframi.feedinggood.utils.DialogWithTwoButtonsUpdating
import com.diusframi.feedinggood.utils.MY_FOOD_DETAIL_KEY
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentFoodEntity: FoodEntity

    private val viewModel: FoodDetailViewModel by viewModels()

    private var modalTwoButtonsUpdating: DialogWithTwoButtonsUpdating? = null

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

        observeViewModel()
        initListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getFoodListVM(currentFoodEntity.id)?.observe(viewLifecycleOwner) {
                loadData(it)
            }
        }
    }

    private fun loadData(foodEntity: FoodEntity) {
        binding.apply {
            tvId.text = foodEntity.id.toString()

            val myDate = foodEntity.date
            val notificationDate = SimpleDateFormat(DATE_FORMAT_FOOD, Locale.getDefault()).format(myDate)
            tvDate.text = notificationDate

            tvName.text = foodEntity.name
            tvType.text = foodEntity.type

            if (foodEntity.isVegetable) {
                tvIsVegetable.text = resources.getString(R.string.food_detail_fragment_is_vegetable)
                binding.clFoodDetail.setBackgroundColor(requireContext().resources.getColor(R.color.green))
            } else {
                tvIsVegetable.text = resources.getString(R.string.food_detail_fragment_is_not_vegetable)
                binding.clFoodDetail.setBackgroundColor(requireContext().resources.getColor(R.color.pink))
            }

            tvCalories.text = resources.getString(R.string.food_detail_fragment_calories, foodEntity.calories)
            tvCarbohydrates.text = resources.getString(R.string.food_detail_fragment_carbohydrates, foodEntity.carbohydrates)
            tvFat.text = resources.getString(R.string.food_detail_fragment_fat, foodEntity.fat)
            tvProteins.text = resources.getString(R.string.food_detail_fragment_proteins, foodEntity.proteins)
        }
    }

    private fun initListeners() {

        binding.apply {
            tvCalories.setOnClickListener {
                showModalTwoButtonsUpdating(currentFoodEntity, 1)
            }
        }

        binding.apply {
            tvCarbohydrates.setOnClickListener {
                showModalTwoButtonsUpdating(currentFoodEntity, 2)
            }
        }

        binding.apply {
            tvFat.setOnClickListener {
                showModalTwoButtonsUpdating(currentFoodEntity, 3)
            }
        }

        binding.apply {
            tvProteins.setOnClickListener {
                showModalTwoButtonsUpdating(currentFoodEntity, 4)
            }
        }
    }

    private fun showModalTwoButtonsUpdating(foodEntity: FoodEntity, option: Int) {

        modalTwoButtonsUpdating = DialogWithTwoButtonsUpdating(
            foodEntity = foodEntity,
            option = option,
            onButtonYesClick = {
                modalTwoButtonsUpdating?.dismiss()
            },
            onButtonNoClick = {
                modalTwoButtonsUpdating?.dismiss()
            }
        )
        modalTwoButtonsUpdating?.show(this.childFragmentManager, modalTwoButtonsUpdating!!.tag)
    }
}