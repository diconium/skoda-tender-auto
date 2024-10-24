package com.skoda.launcher.ui.activity

import android.os.Bundle
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseActivity
import com.skoda.launcher.databinding.ActivityServiceBinding
import com.skoda.launcher.ui.fragment.ServiceListFragment
import com.skoda.launcher.ui.viewmodel.ServiceViewModel

class ServiceActivity :
    BaseActivity<ServiceViewModel, ActivityServiceBinding>(ServiceViewModel::class.java) {
    override fun initViewModel(viewModel: ServiceViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_service
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ServiceListFragment()).commit()
        }
    }
}