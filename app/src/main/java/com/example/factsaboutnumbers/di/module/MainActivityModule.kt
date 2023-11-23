package com.example.factsaboutnumbers.di.module

import com.example.factsaboutnumbers.ui.details.DetailsFragment
import com.example.factsaboutnumbers.ui.list.ListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector
    abstract fun listFragmentInjector(): ListFragment

    @ContributesAndroidInjector
    abstract fun detailsFragmentInjector(): DetailsFragment
}
