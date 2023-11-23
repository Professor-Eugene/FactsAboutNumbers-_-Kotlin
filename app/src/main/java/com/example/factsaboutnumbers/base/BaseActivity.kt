package com.example.factsaboutnumbers.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import butterknife.ButterKnife
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    @LayoutRes
    abstract protected fun layoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes())
        ButterKnife.bind(this)
    }
}