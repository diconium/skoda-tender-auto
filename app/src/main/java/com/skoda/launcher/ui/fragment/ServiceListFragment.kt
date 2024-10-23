package com.skoda.launcher.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skoda.launcher.R
import com.skoda.launcher.api.APIService
import com.skoda.launcher.core.BaseFragment
import com.skoda.launcher.databinding.FragmentServiceListBinding
import com.skoda.launcher.ui.adapter.ServiceListAdapter
import com.skoda.launcher.ui.viewmodel.ServiceViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 * Use the [ServiceListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServiceListFragment :
    BaseFragment<ServiceViewModel, FragmentServiceListBinding>(ServiceViewModel::class.java) {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_service_list
    }

    @Inject
    lateinit var mAPIService: APIService

    override fun init() {
        super.init()
        mainApplication.component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()

        val apiData = liveData(Dispatchers.IO) {
            try {
                val retrivedTodo = mAPIService.getApi()
                emit(retrivedTodo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        apiData.observe(viewLifecycleOwner) {
            Log.i("TAG", "Test: api data" + it.body())
        }
    }

    private fun setupAdapter() {
        val services = mutableListOf<String>()
        services.add("AMBIENT LIGHT")
        services.add("Online Infotainment")
        services.add("AMBIENT LIGHT")
        services.add("AMBIENT LIGHT")

        services.add("AMBIENT LIGHT")
        services.add("AMBIENT LIGHT 2")
        services.add("AMBIENT LIGHT")

        mBinding.serviceListRv.layoutManager =
            GridLayoutManager(requireContext(), 4, RecyclerView.VERTICAL, false)
        mBinding.serviceListRv.adapter =
            ServiceListAdapter(services, object : ServiceListAdapter.ServiceClickListener {
                override fun onClickItem(service: String) {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.fragment_container, ServicePurchaseFragment()).commit()
                }
            })
    }
}