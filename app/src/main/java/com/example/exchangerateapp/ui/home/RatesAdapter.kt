package com.example.exchangerateapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exchangerateapp.R
import com.example.exchangerateapp.data.model.Rate
import com.mynameismidori.currencypicker.ExtendedCurrency
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_rate_view.view.*

class RatesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var ratesList: List<Rate> = ArrayList()


    fun setRatesList(ratesList: List<Rate>) {
        this.ratesList = ratesList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return RateViewHolder(inflater.inflate(R.layout.item_rate_view, parent, false))
    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RateViewHolder).bind(ratesList[position])
    }


    class RateViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {

        fun bind(rate: Rate) {
            containerView.rateCurrencyTv.text = rate.currency
            containerView.rateValueTv.text = rate.exchangeRate.toString()
            containerView.rateIv.setImageResource(ExtendedCurrency.getCurrencyByISO(rate.currency).flag)
        }

    }


}