package com.example.factsaboutnumbers.ui.list

import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.OnClick
import com.example.factsaboutnumbers.R
import com.example.factsaboutnumbers.base.BaseFragment
import com.example.factsaboutnumbers.model.Number
import com.example.factsaboutnumbers.ui.details.DetailsFragment
import com.example.factsaboutnumbers.util.REQUEST
import com.example.factsaboutnumbers.util.SELECT
import javax.inject.Inject


class ListFragment : BaseFragment(), NumberSelectedListener {

    @BindView(R.id.et_number)
    lateinit var editText: EditText

    @BindView(R.id.recyclerView)
    lateinit var listView: RecyclerView

    @BindView(R.id.loading_view)
    lateinit var loadingView: View

    @BindView(R.id.tv_error)
    lateinit var errorTextView: TextView


    private lateinit var listViewModel: ListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ListViewModel

    private lateinit var numbersListAdapter: NumbersListAdapter

    override fun layoutRes(): Int {
        return R.layout.fragment_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ListViewModel::class.java)

        numbersListAdapter = NumbersListAdapter(
            getViewLifecycleOwner(),
            viewModel.numbersLiveData,
            this
        )

        val linearLayoutManager: LinearLayoutManager = object : LinearLayoutManager(activity) {
            override fun onLayoutCompleted(state: RecyclerView.State) {
                super.onLayoutCompleted(state)
                if (numbersListAdapter.getItemCount() != 0) {
                    listView.post(Runnable { listView.smoothScrollToPosition(0) })
                }
            }
        }

        listView.setLayoutManager(linearLayoutManager)
        listView.setAdapter(numbersListAdapter)

        editText.setSaveEnabled(false)
        editText.setOnKeyListener(onEnterKeyListener)

//        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.getUpdateListLiveData().observe(
            viewLifecycleOwner,
            { numbersLiveData: LiveData<List<Number>> ->
                numbersListAdapter.observe(numbersLiveData)
            }
        )
    }

    @Override
    override fun onNumberSelected(number: Number) {
        initDetailsFragment(number.id, SELECT)
    }

    @OnClick(R.id.btn_get_fact)
    fun requestNewFact() {
        val et = editText.text.toString()
        if (!TextUtils.isEmpty(et)) {
            val numberRequested = et.toInt()
            initDetailsFragment(numberRequested, REQUEST)
        }
    }

    @OnClick(R.id.btn_get_random_fact)
    fun requestNewRandomFact() {
        val numberRequested = (Math.random() * 501).toInt()
        initDetailsFragment(numberRequested, REQUEST)
    }

    private fun initDetailsFragment(value: Int, method: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, DetailsFragment.newInstance(value, method))
            .addToBackStack(null)
            .commit()
    }

    var onEnterKeyListener =
        View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == 0) {
                val et = editText.text.toString()
                if (!TextUtils.isEmpty(et)) requestNewFact()
                true
            } else {
                false
            }
        }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_sort_order -> {
                viewModel.toggleSortOrder()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}