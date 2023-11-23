package com.example.factsaboutnumbers.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.factsaboutnumbers.di.util.ViewModelKey
import com.example.factsaboutnumbers.ui.details.DetailsViewModel
import com.example.factsaboutnumbers.ui.list.ListViewModel
import com.example.factsaboutnumbers.util.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    fun bindListViewModel(listViewModel: ListViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(detailsViewModel: DetailsViewModel) : ViewModel

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory) : ViewModelProvider.Factory
}