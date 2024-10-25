package com.skoda.launcher.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseFragment
import com.skoda.launcher.data.source.response.ApiResult
import com.skoda.launcher.data.source.response.SubscriptionStatus
import com.skoda.launcher.data.source.response.Subscriptions
import com.skoda.launcher.databinding.FragmentServiceListBinding
import com.skoda.launcher.ui.adapter.ServiceListAdapter
import com.skoda.launcher.ui.viewmodel.ServiceViewModel
import java.util.Objects.isNull


/**
 * Fragment for displaying a list of services and subscriptions.
 *
 * This fragment shows subscriptions in a tabbed layout, allowing users to filter
 * between active subscriptions and all subscriptions. It observes the [ServiceViewModel]
 * for updates and displays the data in a [RecyclerView].
 */
class ServiceListFragment :
    BaseFragment<ServiceViewModel, FragmentServiceListBinding>(ServiceViewModel::class.java) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_service_list
    }

    /**
     * Boolean indicating whether to filter for active subscriptions.
     */
    private var mFilterActive: Boolean = true

    /**
     * Tag for logging purposes.
     */
    private val TAG: String = ServiceListFragment::class.java.simpleName

    /**
     * Adapter for displaying the list of subscriptions.
     */
    private lateinit var serviceListAdapter: ServiceListAdapter

    /**
     * List of all subscriptions retrieved from the API.
     */
    private var mAllSubscriptions: ArrayList<Subscriptions>? = null

    /**
     * Initializes the fragment and injects dependencies.
     */
    override fun init() {
        super.init()
        mainApplication.component.inject(this)
    }

    /**
     * Called when the fragment's view is created. Sets up the tab layout and
     * observes data from the ViewModel.
     *
     * @param view The view returned by [onCreateView].
     * @param savedInstanceState A Bundle containing the activity's previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activeTab = mBinding.tabLayout.newTab()
        activeTab.text = getString(R.string.active_subscription)
        mBinding.tabLayout.addTab(activeTab)

        var allTab = mBinding.tabLayout.newTab()
        allTab.text = getString(R.string.all)
        mBinding.tabLayout.addTab(allTab)

        mBinding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val isActive = tab == activeTab
                mFilterActive = isActive
                applyFilter(isActive)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        mBinding.backBtn.setOnClickListener {
            requireActivity().finish()
        }
        subscriptionsDataObserver()
        viewModel.getSubscriptionsData()
        setAdapter(arrayListOf())
    }

    /**
     * Applies the active filter to the list of subscriptions.
     *
     * @param isActive Boolean indicating whether to filter for active subscriptions.
     */
    private fun applyFilter(isActive: Boolean) {
        if (mAllSubscriptions == null) {
            Log.i(TAG, "filter: subscription data not available")
            return
        }
        if (isActive) {
            val filterData =
                mAllSubscriptions!!.filter { it.status == SubscriptionStatus.ACTIVATED }
            serviceListAdapter.setList(filterData)
        } else {
            serviceListAdapter.setList(mAllSubscriptions!!)
        }
    }

    /**
     * Observes the LiveData from the ViewModel to update the UI based on the
     * API result.
     */
    private fun subscriptionsDataObserver() {
        viewModel.subscriptionsLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ApiResult.Loading -> {
                    Log.i(TAG, "subscriptionsDataObserver: loading ")
                }
                is ApiResult.Success -> {
                    if (!isNull(response.data)) {
                        response.data?.let {
                            mAllSubscriptions = it.subscriptions
                            applyFilter(mFilterActive)
                        }
                    }
                }
                is ApiResult.Error -> {
                    Log.i(TAG, "subscriptionsDataObserver: Error " + response.message)
                    Snackbar.make(
                        mBinding.serviceListRv, getString(R.string.error_message, response.message),
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is ApiResult.Nothing -> {
                    Log.i(TAG, "subscriptionsDataObserver: Nothing ")
                }
            }
        }
    }

    /**
     * Sets up the RecyclerView with the provided list of subscriptions.
     *
     * @param subscriptions The list of subscriptions to display.
     */
    private fun setAdapter(subscriptions: ArrayList<Subscriptions>) {
        mBinding.serviceListRv.layoutManager =
            GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
        serviceListAdapter =
            ServiceListAdapter(subscriptions, object : ServiceListAdapter.ServiceClickListener {
                override fun onClickItem(subscriptions: Subscriptions) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, ServiceDetailFragment.getInstance(subscriptions.id!!)).commit()
                }
            })
        mBinding.serviceListRv.adapter = serviceListAdapter
    }
}
