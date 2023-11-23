package com.example.factsaboutnumbers.main

import android.os.Bundle
import com.example.factsaboutnumbers.R
import com.example.factsaboutnumbers.base.BaseActivity
import com.example.factsaboutnumbers.ui.list.ListFragment

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(savedInstanceState == null)
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, ListFragment()).commit();
    }
}