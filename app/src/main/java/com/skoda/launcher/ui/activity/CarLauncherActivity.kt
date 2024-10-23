package com.skoda.launcher.ui.activity

import android.content.Intent
import android.os.Bundle
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseActivity
import com.skoda.launcher.databinding.ActivityCarLauncherBinding
import com.skoda.launcher.ui.fragment.MapboxFragment
import com.skoda.launcher.ui.fragment.MediaPlayerFragment
import com.skoda.launcher.ui.fragment.ServiceListFragment
import com.skoda.launcher.ui.viewmodel.CarViewModel

class CarLauncherActivity :
    BaseActivity<CarViewModel, ActivityCarLauncherBinding>(CarViewModel::class.java) {
    override fun initViewModel(viewModel: CarViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_car_launcher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            initFragments()
        }
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.toolbar.setOnClickListener {
            //supportFragmentManager.beginTransaction().add(android.R.id.content, ServiceListFragment()).commit()
           startActivity(Intent(this@CarLauncherActivity, ServiceActivity::class.java))
        }
    }

    private fun initFragments() {
        supportFragmentManager.beginTransaction().add(R.id.maps_card, MapboxFragment()).commit()
        supportFragmentManager.beginTransaction().add(R.id.media_card, MediaPlayerFragment())
            .commit()
    }
}
