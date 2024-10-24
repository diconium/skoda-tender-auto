package com.skoda.launcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseListAdapter
import com.skoda.launcher.data.source.response.SubscriptionStatus
import com.skoda.launcher.data.source.response.Subscriptions
import com.skoda.launcher.databinding.ServiceListItemBinding
import com.skoda.launcher.utils.extensions.DateUtils
import com.skoda.launcher.utils.extensions.getColorCompat
import com.squareup.picasso.Picasso

/**
 * Adapter for displaying a list of subscription services in a RecyclerView.
 *
 * This adapter binds subscription data to the views and handles click events
 * for each item in the list.
 *
 * @param dataList The list of subscriptions to display.
 * @param serviceClickListener The listener for handling item click events.
 */
class ServiceListAdapter(
    private val dataList: List<Subscriptions>,
    private val serviceClickListener: ServiceClickListener
) : BaseListAdapter<Subscriptions>(dataList) {

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
        fun onClickItem(subscriptions: Subscriptions)
    }

    /**
     * Creates a new binding for a service list item.
     *
     * @param parent The parent view group to which the new view will be attached.
     * @param viewType The view type of the new view.
     * @return A [ViewDataBinding] instance for the service list item.
     */
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ServiceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    /**
     * Binds data to the provided binding for the specified position in the list.
     *
     * @param binding The binding for the item view.
     * @param position The position of the item in the list.
     */
    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ServiceListItemBinding) {
            val context = binding.root.context
            val itemBinding: ServiceListItemBinding = binding
            val subscription = list[position]

            itemBinding.serviceTitleIv.text = subscription.name
            itemBinding.serviceItemLy.setOnClickListener {
                serviceClickListener.onClickItem(subscription)
            }

            subscription.imageLink?.let {
                Picasso.get().load(it).into(itemBinding.serviceArtIv)
            }

            when (subscription.status) {
                SubscriptionStatus.ACTIVATED -> {
                    itemBinding.serviceStatus.setBackgroundColor(context.getColorCompat(R.color.md_theme_light_primary))
                    itemBinding.serviceStatus.text =
                        subscription.endDate?.let { DateUtils.subDateFormater(it, false, context) }
                }
                SubscriptionStatus.INACTIVE -> {
                    itemBinding.serviceStatus.setBackgroundColor(context.getColorCompat(R.color.white))
                    itemBinding.serviceStatus.text =
                        subscription.endDate?.let { DateUtils.subDateFormater(it, true, context) }
                }
            }
        }
    }
}
