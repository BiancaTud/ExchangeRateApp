package com.example.exchangerateapp.ui

import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

open class BaseFragment : Fragment() {

    protected val baseViewModel: BaseViewModel by viewModel()



}