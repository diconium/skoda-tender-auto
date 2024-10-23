package com.skoda.launcher.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.launcher.core.BaseListAdapter
import com.skoda.launcher.databinding.ServiceListItemBinding

class ServiceListAdapter(
    private val dataList: List<String>,
    private val serviceClickListener: ServiceClickListener
) : BaseListAdapter<String>(dataList) {

    @FunctionalInterface
    interface ServiceClickListener {
        fun onClickItem(service: String)
    }


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ServiceListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ServiceListItemBinding) {
            val i: ServiceListItemBinding = binding
            val currentItem = dataList[position]
            i.serviceTitleIv.text = currentItem
            i.serviceItemLy.setOnClickListener {
                serviceClickListener.onClickItem(currentItem)
            }
        }
    }
}