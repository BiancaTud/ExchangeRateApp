package com.example.exchangerateapp.ui.home


import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment
import com.example.exchangerateapp.ui.MainActivity
import com.example.exchangerateapp.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment() {

    private val viewModel: HomeViewModel by viewModel()
    private val sharedViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
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

    override fun onStart() {
        super.onStart()
        refreshExchangeRates()

    }

    override fun onStop() {
        super.onStop()
        viewModel.stopPooling()
    }

    private fun refreshExchangeRates() {
        viewModel.getExchangeRatesFor(
            sharedViewModel.currentCurrency,
            sharedViewModel.currentRefreshValue
        )
    }

    private fun goToHistory() {
        findNavController().navigate(R.id.action_homeFragment_to_historyFragment)
    }

    private fun goToSettings() {
        findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        initViews()
        bindViewModel()
    }

    private fun initToolbar() {
        (activity as MainActivity).supportActionBar?.apply {
            title = getString(R.string.app_name)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }
    }

    private fun initViews() {
        val layoutManager = LinearLayoutManager(activity)
        ratesRecyclerView.layoutManager = layoutManager
        ratesRecyclerView.adapter = RatesAdapter()


        context?.let {
            swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimary
                )
            )
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.stopPooling()
            refreshExchangeRates()
        }
    }

    private fun bindViewModel() {
        viewModel.ratesList.observe(viewLifecycleOwner, Observer { ratesList ->
            progressBar.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            swipeRefreshLayout.visibility = View.VISIBLE
            sharedViewModel.currenciesList = ratesList.map { rate -> rate.currency }.toMutableList()
            (ratesRecyclerView.adapter as RatesAdapter).setRatesList(ratesList)
        })
        viewModel.timestamp.observe(viewLifecycleOwner, Observer { dateString ->
            lastCheckTv.text = getString(R.string.last_check) + " " + dateString
        })
        viewModel.showErrorConnectionEvent.observe(viewLifecycleOwner, Observer { msg ->
            progressBar.visibility = View.GONE
            Toast.makeText(
                activity,
                msg,
                Toast.LENGTH_SHORT
            ).show()
        })
        viewModel.showProgressEvent.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = View.VISIBLE
        })
    }
}
