package com.skoda.launcher.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseActivity
import com.skoda.launcher.data.source.remote.config.APIConfig
import com.skoda.launcher.databinding.ActivityCarLauncherBinding
import com.skoda.launcher.model.SettingMenu
import com.skoda.launcher.model.SettingMenuUtils.settingMenus
import com.skoda.launcher.ui.adapter.SettingMenuAdapter
import com.skoda.launcher.ui.viewmodel.CarViewModel

class CarLauncherActivity :
    BaseActivity<CarViewModel, ActivityCarLauncherBinding>(CarViewModel::class.java) {

    private lateinit var settingMenuAdapter: SettingMenuAdapter
    private val TAG = CarLauncherActivity::class.java.simpleName

    override fun initViewModel(viewModel: CarViewModel) {
        binding.viewModel = viewModel
    }

    override fun getLayoutRes() = R.layout.activity_car_launcher

    // Launcher for requesting notification permissions
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, show the notification
        } else {
            // Handle the case where the permission is denied
            Log.i(TAG, "Notification permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupClickListeners()
        viewModel.getDriverDistractionStates()
        requestNotificationPermission()

        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_AUTOMOTIVE)) {
            Log.i(TAG, "onCreate: FEATURE_AUTOMOTIVE not available ")
            Snackbar.make(
                binding.menuRv, getString(R.string.automotive_not_available),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun setupClickListeners() {
        viewModel.uiUxEnableState.observe(this) {
            settingMenuAdapter.setUiUxRestriction(it)
        }
        val listMenu = settingMenus(this)
        settingMenuAdapter =
            SettingMenuAdapter(listMenu, object : SettingMenuAdapter.ServiceClickListener {
                override fun onClickItem(settingMenu: SettingMenu) {
                    if (settingMenu.title === getString(R.string.shop)) {
                        startActivity(
                            Intent(
                                this@CarLauncherActivity,
                                SubscriptionActivity::class.java
                            )
                        )
                    }
                }
            })
        binding.menuRv.adapter = settingMenuAdapter
        binding.menuRv.layoutManager =
            GridLayoutManager(this, 5, RecyclerView.VERTICAL, false)

        val topic = APIConfig.VIN_NO
        Firebase.messaging.subscribeToTopic(topic).addOnCompleteListener {
            Log.i(
                TAG, "subscribed: $topic ${it.isSuccessful}"
            )
        }


    }

    private fun requestNotificationPermission() {
        Log.i(TAG, "Attempting to show notification")
        // Request notification permission for Android 13+ (Tiramisu)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // Permission is already granted, so show the notification
                }

                else -> {
                    // Request the permission
                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }

    }


}
