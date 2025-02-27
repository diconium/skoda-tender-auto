package com.skoda.launcher.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.skoda.launcher.MainApplication

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(private val mViewModelClass: Class<VM>) :
    androidx.fragment.app.Fragment() {
    lateinit var viewModel: VM
    open lateinit var mBinding: DB
    val mainApplication: MainApplication
        get() {
            return requireActivity().application as MainApplication
        }

    private fun init(inflater: LayoutInflater, container: ViewGroup) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    open fun init() {}

    @LayoutRes
    abstract fun getLayoutRes(): Int

    private fun getViewM(): VM = ViewModelProvider(requireActivity()).get(mViewModelClass)
    open fun onInject() {}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewM()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container!!)
        init()
        super.onCreateView(inflater, container, savedInstanceState)
        return mBinding.root
    }
}