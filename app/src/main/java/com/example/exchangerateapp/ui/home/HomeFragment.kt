package com.example.exchangerateapp.ui.home


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {


    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                goToSettings()
            }
            R.id.action_history -> {
                goToHistory()
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        refreshExchangeRates()

    }

    override fun onPause() {
        super.onPause()
        viewModel.stopPooling()
    }

    private fun refreshExchangeRates() {
        viewModel.getExchangeRatesFor()
    }

    private fun goToHistory() {
        findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
    }

    private fun goToSettings() {
        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        bindViewModel()
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        ratesRecyclerView.layoutManager = layoutManager
        ratesRecyclerView.adapter = RatesAdapter()
    }

    private fun bindViewModel() {
        viewModel.ratesList.observe(viewLifecycleOwner, Observer { ratesList ->
            (ratesRecyclerView.adapter as RatesAdapter).setRatesList(ratesList)
        })
        viewModel.timestamp.observe(viewLifecycleOwner, Observer { dateString ->
            lastCheckTv.text = getString(R.string.last_check) + " " + dateString
        })
    }
}
