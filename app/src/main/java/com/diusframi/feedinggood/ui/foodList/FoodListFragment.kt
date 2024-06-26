package com.diusframi.feedinggood.ui.foodList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.diusframi.feedinggood.FeedingGoodApplication.Companion.preferences
import com.diusframi.feedinggood.MainActivity
import com.diusframi.feedinggood.R
import com.diusframi.feedinggood.data.localdb.model.FoodEntity
import com.diusframi.feedinggood.databinding.FragmentFoodListBinding
import com.diusframi.feedinggood.utils.DATE_FORMAT_FOOD
import com.diusframi.feedinggood.utils.DialogWithTwoButtons
import com.diusframi.feedinggood.utils.MY_FOOD_DETAIL_KEY
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class FoodListFragment : Fragment(), FoodListAdapter.ItemClickListener {

    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodListViewModel by viewModels()

    private var modalTwoButtons: DialogWithTwoButtons? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar!!.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFoodListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        initListeners()
    }

    override fun onResume() {
        super.onResume()

        (requireActivity() as MainActivity).supportActionBar!!.hide()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.getFoodListVM()?.observe(viewLifecycleOwner) {
                loadData(it)
            }
        }
    }

    private fun initListeners() {
        binding.fabTest.setOnClickListener {
            showModalTwoButtons()
        }

        binding.ivDeleteAll.setOnClickListener {

            val areYouSure = AlertDialog.Builder(requireActivity())
            areYouSure.setMessage(R.string.alert_are_you_sure_text)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_accept) { _, _ ->
                    viewModel.deleteAllFoodVM()
                }
                .setNegativeButton(R.string.alert_cancel) { _, _ ->}
            areYouSure.show()
        }

        binding.ivLogout.setOnClickListener {

            val areYouSure = AlertDialog.Builder(requireActivity())
            areYouSure.setMessage(R.string.alert_are_you_sure_logout)
                .setCancelable(false)
                .setPositiveButton(R.string.alert_accept) { _, _ ->
                    preferences.saveKeepSession(false)

                    activity?.supportFragmentManager?.popBackStack()
                }
                .setNegativeButton(R.string.alert_cancel) { _, _ ->}
            areYouSure.show()
        }

        binding.ivUserList.setOnClickListener {
            findNavController().navigate(R.id.userListFragment)
        }
    }

    private fun loadData(list: List<FoodEntity>?) {

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

            binding.rvLogs.adapter = FoodListAdapter(requireContext(), collectionsReverse(list), this)
        }
    }

    private fun collectionsReverse(source: List<FoodEntity>): List<FoodEntity> {
        return ArrayList(source).reversed()
    }

    private fun showModalTwoButtons() {

        modalTwoButtons = DialogWithTwoButtons()
        modalTwoButtons?.show(this.childFragmentManager, modalTwoButtons!!.tag)
    }

    override fun onItemClick(item: FoodEntity) {
        val bundle = Bundle()
        bundle.putSerializable(MY_FOOD_DETAIL_KEY, item)
        findNavController().navigate(R.id.navigation_food_detail_fragment, bundle)
    }

    override fun onLongItemClick(item: FoodEntity) {

        val areYouSure = AlertDialog.Builder(requireActivity())
        areYouSure.setMessage(R.string.alert_are_you_sure_text_single)
            .setCancelable(false)
            .setPositiveButton(R.string.alert_accept) { _, _ ->
                viewModel.deleteFoodVM(item)
            }
            .setNegativeButton(R.string.alert_cancel) { _, _ ->}
        areYouSure.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}