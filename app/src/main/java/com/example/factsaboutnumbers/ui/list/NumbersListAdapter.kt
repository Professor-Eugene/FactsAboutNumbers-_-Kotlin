package com.example.factsaboutnumbers.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.example.factsaboutnumbers.R
import com.example.factsaboutnumbers.model.Number

class NumbersListAdapter constructor(
    var lifecycleOwner: LifecycleOwner,
    var numbersLiveData: LiveData<List<Number>>,
    private val numberSelectedListener: NumberSelectedListener
) : RecyclerView.Adapter<NumbersListAdapter.NumberViewHolder>() {

    private var data: List<Number> = ArrayList()

    init {
        setHasStableIds(true)
        observe(numbersLiveData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.numbers_list_item, parent, false)
        return NumberViewHolder(view, numberSelectedListener)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return data[position].id.toLong()
    }

    fun observe(numbersLiveData: LiveData<List<Number>>) {
        this.numbersLiveData.removeObservers(lifecycleOwner)
        this.numbersLiveData = numbersLiveData

        numbersLiveData.observe(lifecycleOwner) { numbers: List<Number> ->
            data = numbers
            notifyDataSetChanged()
        }
    }

    class NumberViewHolder(
        itemView: View,
        numberSelectedListener: NumberSelectedListener
    ) :
        RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_number)
        lateinit var numberTextView: TextView

        @BindView(R.id.tv_fact)
        lateinit var factTextView: TextView

        private var number: Number? = null

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener { v: View ->
                if (number != null) {
                    numberSelectedListener.onNumberSelected(number!!)
                }
            }
        }

        fun bind(numberObject: Number) {
            this.number = numberObject

            val stringValueOfNumber = numberObject.number.toString()
            numberTextView.text = stringValueOfNumber

            val rawFact = numberObject.fact
            val fact = reformatFact(rawFact, stringValueOfNumber)
            factTextView.text = fact
        }

        fun reformatFact(rawFact: String, stringValueOfNumber: String): String {
            val fact = rawFact.replaceFirst("$stringValueOfNumber ", "")
            return fact
        }
    }
}