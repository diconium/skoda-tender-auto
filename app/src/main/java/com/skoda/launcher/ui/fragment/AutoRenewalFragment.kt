package com.skoda.launcher.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.skoda.launcher.R
import com.skoda.launcher.ui.viewmodel.ServiceViewModel


class AutoRenewalFragment : Fragment() {

    var isFinishByOrderClick = false
    private lateinit var viewModel: ServiceViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(ServiceViewModel::class.java)
    }

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
            isFinishByOrderClick = true
            requireActivity().finish()

        }

        val backBtnListener: (v: View) -> Unit = {
            requireActivity().supportFragmentManager.popBackStack()
        }
        view.findViewById<View>(R.id.back_btn).setOnClickListener(backBtnListener)
        view.findViewById<View>(R.id.back_btn_prev).setOnClickListener(backBtnListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (!isFinishByOrderClick) {
            viewModel.sendNotification()
        }
    }

}