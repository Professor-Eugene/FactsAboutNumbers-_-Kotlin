package com.example.factsaboutnumbers.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.Unbinder
import dagger.android.support.DaggerFragment

abstract class BaseFragment : DaggerFragment() {

    lateinit private var unbinder: Unbinder

    @LayoutRes
    abstract protected fun layoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layoutRes(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view

    }

    override fun onDestroyView() {
        super.onDestroyView()
            unbinder.unbind()
    }
}