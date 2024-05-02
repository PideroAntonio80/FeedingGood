package com.diusframi.feedinggood.ui.userList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.diusframi.feedinggood.FeedingGoodApplication.Companion.preferences
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.model.UserLoginEntity
import com.diusframi.feedinggood.databinding.FragmentUserListBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class UserListFragment : Fragment(), UserListAdapter.ItemClickListener {

    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        initListeners()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getUserListVM()?.observe(viewLifecycleOwner) {
                loadData(it)
            }
        }
    }

    private fun initListeners() {
        binding.ivDeleteAll.setOnClickListener {

            val areYouSure = AlertDialog.Builder(requireActivity())
            areYouSure.setMessage(R.string.alert_are_you_sure_text)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_accept) { _, _ ->
                    viewModel.deleteAllUsersVM()
                }
                .setNegativeButton(R.string.alert_cancel) { _, _ ->}
            areYouSure.show()
        }
    }

    private fun loadData(list: List<UserLoginEntity>?) {

        if (list.isNullOrEmpty()) {
            binding.rvLogs.visibility = View.GONE
            binding.tvEmptyList.visibility = View.VISIBLE
        }
        else {
            binding.rvLogs.visibility = View.VISIBLE
            binding.tvEmptyList.visibility = View.GONE

            binding.tvUser.text = resources.getString(R.string.food_list_user_title, preferences.getUserName())

            val myDate = preferences.getUserDate()
            val notificationDate = SimpleDateFormat(DATE_FORMAT_FOOD, Locale.getDefault()).format(myDate)
            binding.tvDate.text = notificationDate

            val layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.VERTICAL, false)
            binding.rvLogs.layoutManager = layoutManager
            binding.rvLogs.setHasFixedSize(true)

            binding.rvLogs.adapter = UserListAdapter(requireContext(), collectionsReverse(list), this)
        }
    }

    private fun collectionsReverse(source: List<UserLoginEntity>): List<UserLoginEntity> {
        return ArrayList(source).reversed()
    }

    override fun onLongItemClick(item: UserLoginEntity) {
        val areYouSure = AlertDialog.Builder(requireActivity())
        areYouSure.setMessage(R.string.alert_are_you_sure_text_single)
            .setCancelable(false)
            .setPositiveButton(R.string.alert_accept) { _, _ ->
                viewModel.deleteUserVM(item)
            }
            .setNegativeButton(R.string.alert_cancel) { _, _ ->}
        areYouSure.show()
    }
}