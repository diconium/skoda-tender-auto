package com.skoda.launcher.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseFragment
import com.skoda.launcher.databinding.FragmentServiceDetailBinding
import com.skoda.launcher.ui.viewmodel.ServiceViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ServiceListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ServiceDetailFragment :
    BaseFragment<ServiceViewModel, FragmentServiceDetailBinding>(ServiceViewModel::class.java) {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_service_detail
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
    }

    private fun setupAdapter() {

    }
}