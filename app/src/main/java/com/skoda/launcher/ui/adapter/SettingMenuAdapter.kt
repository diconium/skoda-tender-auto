package com.skoda.launcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseListAdapter
import com.skoda.launcher.databinding.SettingMenuItemBinding
import com.skoda.launcher.model.SettingMenu
import com.skoda.launcher.utils.extensions.getColorCompat

/**
 * Adapter for displaying a list of subscription services in a RecyclerView.
 *
 * This adapter binds subscription data to the views and handles click events
 * for each item in the list.
 *
 * @param dataList The list of subscriptions to display.
 * @param serviceClickListener The listener for handling item click events.
 */
class SettingMenuAdapter(
    private val dataList: List<SettingMenu>,
    private val serviceClickListener: ServiceClickListener
) : BaseListAdapter<SettingMenu>(dataList) {

    private var misUiUxRestriction: Boolean? = null

    /**
     * Functional interface for handling click events on service items.
     */
    @FunctionalInterface
    interface ServiceClickListener {
        /**
         * Called when a service item is clicked.
         *
         * @param subscriptions The clicked subscription item.
         */
        fun onClickItem(settingMenu: SettingMenu)
    }

    /**
     * Creates a new binding for a service list item.
     *
     * @param parent The parent view group to which the new view will be attached.
     * @param viewType The view type of the new view.
     * @return A [ViewDataBinding] instance for the service list item.
     */
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return SettingMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    /**
     * Binds data to the provided binding for the specified position in the list.
     *
     * @param binding The binding for the item view.
     * @param position The position of the item in the list.
     */
    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is SettingMenuItemBinding) {
            val context = binding.root.context
            val itemBinding: SettingMenuItemBinding = binding
            val subscription = list[position]

            itemBinding.menuTv.text = subscription.title
            itemBinding.menuIv.setImageResource(subscription.icon)
            itemBinding.root.setOnClickListener {
                serviceClickListener.onClickItem(subscription)
            }
            if (!subscription.allowDistraction) {
                if (misUiUxRestriction == false) {
                    itemBinding.menuCard.setCardBackgroundColor(context.getColorCompat(R.color.dray_gray))
                } else {
                    itemBinding.menuCard.setCardBackgroundColor(context.getColorCompat(R.color.light_gray))
                }
            } else {
                itemBinding.menuCard.setCardBackgroundColor(context.getColorCompat(R.color.dray_gray))
            }
        }
    }

    fun setUiUxRestriction(isUiUxRestriction: Boolean?) {
        misUiUxRestriction = isUiUxRestriction
        notifyDataSetChanged()
    }
}
