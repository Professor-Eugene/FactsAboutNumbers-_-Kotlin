package com.example.factsaboutnumbers.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.factsaboutnumbers.di.util.AppScope
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class ViewModelFactory @Inject constructor(
    private val creators: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
    ) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator: Provider<ViewModel>? = creators[modelClass]
        if (creator == null) {
            for (entry: Map.Entry<Class<out ViewModel>, Provider<ViewModel>> in creators.entries) {
                if (modelClass.isAssignableFrom(entry.key)) {
                    creator = entry.value;
                    break;
                }
            }
        }
        if (creator == null) {
            throw IllegalArgumentException("unknown model class " + modelClass);
        }

        try {
            val viewModel = creator.get();
            return viewModel as T;
        } catch (e: Exception) {
            throw RuntimeException(e);
        }
    }
}