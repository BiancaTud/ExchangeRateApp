package com.example.exchangerateapp.ui.history


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment

/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }


}