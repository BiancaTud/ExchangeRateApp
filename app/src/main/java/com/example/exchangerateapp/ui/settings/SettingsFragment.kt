package com.example.exchangerateapp.ui.settings


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment
import android.widget.ArrayAdapter
import com.example.exchangerateapp.ui.MainActivity
import com.example.exchangerateapp.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment() {

    private val sharedViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        setUpFields()
    }


    private fun initToolbar() {
        (activity as MainActivity).supportActionBar?.apply {
            title = getString(R.string.settings)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

    }

    private fun setUpFields() {


        context?.let { context ->
            val adapter = ArrayAdapter(
                context,
                R.layout.item_spinner,
                MainViewModel.refreshValuesList
            )

            refreshExposedDropdown.setAdapter(adapter)
            refreshExposedDropdown.setText(sharedViewModel.currentRefreshValue.toString(), false)
            refreshExposedDropdown.setOnItemClickListener { _, _, position, _ ->
                if (position < MainViewModel.refreshValuesList.size) {
                    sharedViewModel.currentRefreshValue = MainViewModel.refreshValuesList[position]
                }
            }

            if (!sharedViewModel.currenciesList.contains(sharedViewModel.currentCurrency)) {
                sharedViewModel.currenciesList.add(sharedViewModel.currentCurrency)
            }

            val adapterCurrencies = ArrayAdapter(
                context,
                R.layout.item_spinner,
                sharedViewModel.currenciesList
            )

            currencyExposedDropdown.setAdapter(adapterCurrencies)
            currencyExposedDropdown.setText(sharedViewModel.currentCurrency, false)
            currencyExposedDropdown.setOnItemClickListener { _, _, position, _ ->
                if (position < sharedViewModel.currenciesList.size) {
                    sharedViewModel.currentCurrency = sharedViewModel.currenciesList[position]
                }
            }
        }
    }
}
