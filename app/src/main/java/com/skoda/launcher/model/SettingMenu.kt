package com.skoda.launcher.model

import android.content.Context
import com.skoda.launcher.R

data class SettingMenu(val icon: Int, val title: String, val allowDistraction: Boolean)

object SettingMenuUtils {
    fun settingMenus(context: Context): ArrayList<SettingMenu> {
        val listMenu = arrayListOf<SettingMenu>()
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_power,
                context.getString(R.string.powerpass),
                true
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_drive_mode,
                context.getString(R.string.driver_mode), true
            )
        )

        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_parking,
                context.getString(R.string.parking),
                false
            )
        )

        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_parkheater,
                context.getString(R.string.parkheater), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_charging,
                context.getString(R.string.charging),
                true
            )
        )

        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_sound,
                context.getString(R.string.sound), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_seats,
                context.getString(R.string.seats), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_ambiencelight,
                context.getString(R.string.ambient_light), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_digital_dials_setup,
                context.getString(R.string.digital_dials_setup), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_shopping,
                context.getString(R.string.shop), false
            )
        )
        listMenu.add(
            SettingMenu(
                R.drawable.ic_setting_account,
                context.getString(R.string.account),
                false
            )
        )
        return listMenu
    }

}