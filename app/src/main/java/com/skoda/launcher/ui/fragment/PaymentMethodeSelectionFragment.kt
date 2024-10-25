package com.skoda.launcher.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.skoda.launcher.R


class PaymentMethodeSelectionFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_method_selection, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.next_button).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, AutoRenewalFragment()).addToBackStack(null).commit()

        }

        view.findViewById<View>(R.id.back_btn).setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}