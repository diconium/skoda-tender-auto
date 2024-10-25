package com.skoda.launcher.ui.fragment


import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.skoda.launcher.R
import com.skoda.launcher.core.BaseFragment
import com.skoda.launcher.data.source.response.SubscriptionStatus
import com.skoda.launcher.data.source.response.Subscriptions
import com.skoda.launcher.databinding.FragmentServiceDetailBinding
import com.skoda.launcher.ui.viewmodel.ServiceViewModel
import com.skoda.launcher.utils.extensions.DateUtils
import com.squareup.picasso.Picasso


class ServiceDetailFragment :
    BaseFragment<ServiceViewModel, FragmentServiceDetailBinding>(ServiceViewModel::class.java) {
    private var subId: Int = 0

    override fun getLayoutRes(): Int {
        return R.layout.fragment_service_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt("id", 0)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val subscriptionClickListener: (v: View) -> Unit = {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ServicePurchaseFragment()).addToBackStack(null)
                .commit()

        }

        mBinding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        mBinding.renewSubBtn.setOnClickListener(subscriptionClickListener)
        mBinding.ivExtendLicense.setOnClickListener(subscriptionClickListener)
        val subscription = viewModel.getSubscriptionById(subId)
        updateUi(subscription)

    }

    private fun updateUi(subscription: Subscriptions?) {
        subscription?.let {
            mBinding.tvDescription.text = subscription.description
            mBinding.toolbarTv.text = subscription.name
            mBinding.subscriptionTitleTv.text = subscription.name
            mBinding.subscriptionDescTv.text = subscription.name
            if (subscription.endDate != null) {
                val dateFormater = DateUtils.dateFormater(subscription.endDate!!)
                mBinding.tvExpiredDescription.text =
                    getString(R.string.subscription_expires_extend, dateFormater)
            }
            Picasso.get().load(subscription.imageLink).into(mBinding.subscriptionBannerIv)

            if (subscription.status == SubscriptionStatus.ACTIVATED) {

            } else {
                mBinding.ivExtendLicense.isVisible = false
            }

        }
    }

    companion object {
        fun getInstance(subId: Int): ServiceDetailFragment {
            val serviceDetailFragment = ServiceDetailFragment();
            val bundle = Bundle()
            bundle.putInt("id", subId)
            serviceDetailFragment.arguments = bundle
            return serviceDetailFragment
        }
    }

}