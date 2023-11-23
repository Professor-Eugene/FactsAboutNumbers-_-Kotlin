package com.example.factsaboutnumbers.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import com.example.factsaboutnumbers.R
import com.example.factsaboutnumbers.base.BaseFragment
import com.example.factsaboutnumbers.util.REQUEST
import com.example.factsaboutnumbers.util.SELECT
import com.example.factsaboutnumbers.util.log
import javax.inject.Inject

class DetailsFragment : BaseFragment() {
    @BindView(R.id.details_number)
    lateinit var numberTextView: TextView

    @BindView(R.id.details_text)
    lateinit var factTextView: TextView

    @BindView(R.id.details_loading_view)
    lateinit var loadingView: View

    @BindView(R.id.details_tv_error)
    lateinit var errorTextView: TextView

    lateinit var actionBar: ActionBar

    override fun layoutRes(): Int {
        return R.layout.fragment_details
    }

    private lateinit var viewModel: DetailsViewModel

    companion object {

        private const val ARG_VALUE = "number"
        private const val ARG_METHOD = "method"

        fun newInstance(number: Int, method: String): DetailsFragment {
            val fragment = DetailsFragment()
            val args = Bundle()
            args.putInt(ARG_VALUE, number)
            args.putString(ARG_METHOD, method)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBar = (activity as AppCompatActivity).supportActionBar!!
        actionBar.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, viewModelFactory).get(
            DetailsViewModel::class.java
        )

        val method = requireArguments().getString(ARG_METHOD)
        val value = requireArguments().getInt(ARG_VALUE)

        if (method == REQUEST) {
            val number: Int = value
            viewModel.requestNewFact(number)
        } else if (method == SELECT) {
            val id: Int = value
            viewModel.getNumberSelectedFromList(id)
        }

        observeViewModel()
    }

    fun observeViewModel() {

        viewModel.numberLiveData.observe(viewLifecycleOwner) { number ->
            val stringNumber = number.number.toString()
            numberTextView.text = stringNumber
            val rawFact = number.fact
            val fact = rawFact.replaceFirst("$stringNumber ".toRegex(), "")
            factTextView.text = fact
        }

        viewModel.loading.observe(

            viewLifecycleOwner
        ) { isLoading: Boolean? ->
            if (isLoading != null) {
                if (isLoading) {
                    numberTextView.text = ""
                    factTextView.text = ""
                    loadingView.visibility = View.VISIBLE
                } else {
                    loadingView.visibility = View.GONE
                }
            }
        }

        viewModel.numbersLoadError.observe(
            viewLifecycleOwner
        ) { isError: Boolean? ->
            if (isError != null)
                if (isError) {
                    errorTextView.visibility = View.VISIBLE
                    errorTextView.text = getString(R.string.error_while_loading_data)
                } else {
                    errorTextView.visibility = View.GONE
                    errorTextView.setText(null)
                }
        }

        viewModel.toast.observe(viewLifecycleOwner) { resource ->
            Toast.makeText(activity, resource, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                actionBar.setDisplayHomeAsUpEnabled(false)
                requireActivity().supportFragmentManager.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}