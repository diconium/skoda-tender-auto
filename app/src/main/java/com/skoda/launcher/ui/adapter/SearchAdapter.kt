package com.skoda.launcher.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.skoda.launcher.core.BaseListAdapter
import com.skoda.launcher.databinding.ItemSearchListBinding
import com.skoda.launcher.model.SearchData

class SearchAdapter(
    private val dataList: ArrayList<SearchData>,
    private val searchListener: SearchListener
) :
    BaseListAdapter<SearchData>(dataList) {

    @FunctionalInterface
    interface SearchListener {
        fun onClickItem(position: SearchData)
    }


    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return ItemSearchListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        if (binding is ItemSearchListBinding) {
            val i: ItemSearchListBinding = binding
            val currentItem = dataList[position]
            i.tvSeach.text = currentItem.addressDistance
            i.root.setOnClickListener {
                searchListener.onClickItem(currentItem)
            }
        }
    }
}