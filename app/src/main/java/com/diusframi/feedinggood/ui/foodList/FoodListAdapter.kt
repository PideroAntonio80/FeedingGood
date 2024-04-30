package com.diusframi.feedinggood.ui.foodList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.FoodListItemBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import java.text.SimpleDateFormat
import java.util.Locale

class FoodListAdapter(private val context: Context,
                      private val foodList: List<FoodEntity>,
                      private val itemClickListener: ItemClickListener?
) : RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.food_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val libLogEntity = foodList[position]
        holder.bind(libLogEntity)
    }

    override fun getItemCount(): Int = foodList.size


    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        private val binding = FoodListItemBinding.bind(view)

        fun bind(foodEntity: FoodEntity) {

            binding.tvName.text = foodEntity.name

            binding.tvType.text = foodEntity.type

            val myDate = foodEntity.date
            val notificationDate = SimpleDateFormat(DATE_FORMAT_FOOD, Locale.getDefault()).format(myDate)
            binding.tvDate.text = notificationDate

            if (foodEntity.isVegetable) {
                binding.clStatus.setBackgroundColor(context.resources.getColor(R.color.green))
            }
            else {
                binding.clStatus.setBackgroundColor(context.resources.getColor(R.color.red_dark))
            }

            itemView.setOnClickListener {
                itemClickListener?.onItemClick(foodEntity)
            }
        }
    }

    interface ItemClickListener {
        fun onItemClick(item: FoodEntity)
    }
}