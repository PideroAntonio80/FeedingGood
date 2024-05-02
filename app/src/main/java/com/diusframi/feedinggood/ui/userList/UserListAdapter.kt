package com.diusframi.feedinggood.ui.userList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity
import com.diusframi.feedinggood.databinding.UserListItemBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import java.text.SimpleDateFormat
import java.util.Locale

class UserListAdapter(private val context: Context,
                      private val userList: List<UserLoginEntity>,
                      private val itemClickListener: ItemClickListener?
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userLoginEntity = userList[position]
        holder.bind(userLoginEntity)
    }

    override fun getItemCount(): Int = userList.size


    inner class ViewHolder(view : View): RecyclerView.ViewHolder(view) {
        private val binding = UserListItemBinding.bind(view)

        fun bind(userLoginEntity: UserLoginEntity) {

            binding.tvId.text = userLoginEntity.id.toString()
            binding.tvNameText.text = userLoginEntity.userName
            binding.tvPasswordText.text = userLoginEntity.password

            val myDate = userLoginEntity.date
            val notificationDate = SimpleDateFormat(DATE_FORMAT_FOOD, Locale.getDefault()).format(myDate)
            binding.tvDate.text = notificationDate

            itemView.setOnLongClickListener {
                itemClickListener?.onLongItemClick(userLoginEntity)
                true
            }
        }
    }

    interface ItemClickListener {
        fun onLongItemClick(item: UserLoginEntity)
    }
}