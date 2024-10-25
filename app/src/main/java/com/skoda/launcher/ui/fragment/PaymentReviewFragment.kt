package com.skoda.launcher.ui.fragment

import android.os.Bundle
import android.view.View
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseFragment
import com.skoda.launcher.databinding.FragmentPaymentReviewBinding
import com.skoda.launcher.ui.viewmodel.ServiceViewModel

class PaymentReviewFragment :
    BaseFragment<ServiceViewModel, FragmentPaymentReviewBinding>(ServiceViewModel::class.java) {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_payment_review
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.order_button).setOnClickListener {
            processOrderAction()
        }
        val backBtnListener: (v: View) -> Unit = {
            requireActivity().finish()
        }
        view.findViewById<View>(R.id.back_btn).setOnClickListener(backBtnListener)
        view.findViewById<View>(R.id.back_btn_prev).setOnClickListener(backBtnListener)
    }

    var isFinishByOrderClick = false
    private fun processOrderAction() {
        isFinishByOrderClick = true
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isFinishByOrderClick) {
            viewModel.sendNotification()
        }
    }

}