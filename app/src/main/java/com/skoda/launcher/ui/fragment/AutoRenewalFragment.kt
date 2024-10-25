package com.skoda.launcher.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.skoda.launcher.R


class AutoRenewalFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auto_renewal, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.next_button).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, PaymentReviewFragment()).addToBackStack(null).commit()

        }

        val backBtnListener: (v: View) -> Unit = {
            requireActivity().supportFragmentManager.popBackStack()
        }
        view.findViewById<View>(R.id.back_btn).setOnClickListener(backBtnListener)
        view.findViewById<View>(R.id.back_btn_prev).setOnClickListener(backBtnListener)
    }

}