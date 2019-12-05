package com.example.exchangerateapp.ui.history


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.exchangerateapp.R
import com.example.exchangerateapp.ui.BaseFragment
import com.example.exchangerateapp.ui.MainActivity
import com.example.exchangerateapp.ui.MainViewModel
import com.example.exchangerateapp.ui.history.HistoryViewModel.Companion.currencyBGN
import com.example.exchangerateapp.ui.history.HistoryViewModel.Companion.currencyRON
import com.example.exchangerateapp.ui.history.HistoryViewModel.Companion.currencyUSD
import com.github.mikephil.charting.data.Entry
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlinx.android.synthetic.main.fragment_history.*
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.components.XAxis
import com.mynameismidori.currencypicker.ExtendedCurrency


/**
 * A simple [Fragment] subclass.
 */
class HistoryFragment : BaseFragment() {


    private val viewModel: HistoryViewModel by viewModel()
    private val sharedViewModel: MainViewModel by sharedViewModel()

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
        viewModel.getHistoryRatesFor(sharedViewModel.currentCurrency)
        viewModel.ronDataset.observe(viewLifecycleOwner, Observer { ronDatasetList ->
            run {
                setRONChart(ronDatasetList)
            }
        })
    }

    private fun setRONChart(ronDatasetList: List<Pair<Date, Double>>) {
        val entries = ronDatasetList.map {
            Entry(it.first.time.toFloat(), it.second.toFloat())
        }

        val dataSet = LineDataSet(entries, "RON")
        context?.let { dataSet.color = ContextCompat.getColor(it, R.color.colorPrimaryDark) }
        val lineData = LineData(dataSet)
        ronChart.data = lineData
        ronChart.invalidate()
    }

    private fun initViews() {
        ronChart.legend.isEnabled = false
        ronChart.description.text = getString(R.string.description_graph)
        ronChart.axisLeft.setDrawGridLines(false)
        ronChart.axisRight.setDrawGridLines(false)

        val xAxis = ronChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = DateAxisValueFormatter()

        ronChart.axisRight.isEnabled = false
        ronChart.axisLeft.isEnabled = false

        ronIv.setImageResource(ExtendedCurrency.getCurrencyByISO(currencyRON).flag)
        usdIv.setImageResource(ExtendedCurrency.getCurrencyByISO(currencyUSD).flag)
        bgnIv.setImageResource(ExtendedCurrency.getCurrencyByISO(currencyBGN).flag)
    }

    private fun initToolbar() {
        (activity as MainActivity).supportActionBar?.apply {
            title = getString(com.example.exchangerateapp.R.string.history)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

}


class DateAxisValueFormatter :
    ValueFormatter() {

    private var sdf = SimpleDateFormat("MM-dd")

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return sdf.format(Date(value.toLong())).toString()
    }
}
