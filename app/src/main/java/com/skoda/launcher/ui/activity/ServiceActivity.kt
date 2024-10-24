package com.skoda.launcher.ui.activity

import android.car.Car
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.skoda.launcher.R
import com.skoda.launcher.ui.fragment.ServiceListFragment

class ServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, ServiceListFragment()).commit()
        }

        var car = Car.createCar(this)
        // DriverDistractionManager.init(this, car)
    }
}