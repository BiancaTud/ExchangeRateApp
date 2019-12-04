package com.example.exchangerateapp.ui.history


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment
import com.example.exchangerateapp.ui.MainActivity

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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initViews()
        bindViewModel()
    }

    private fun bindViewModel() {

    }

    private fun initViews() {

    }

    private fun initToolbar() {
        (activity as MainActivity).supportActionBar?.apply {
            title = getString(R.string.history)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


}
