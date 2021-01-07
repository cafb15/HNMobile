package com.macary.hnmobile.utils.coroutines

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by Carlos Farfan on 4/01/2021.
 */
abstract class ScopedViewModel(uiDispatcher: CoroutineDispatcher) : ViewModel(),
    IScope by IScope.Impl(uiDispatcher) {

    init {
        this.initScope()
    }

    override fun onCleared() {
        destroyScope()
        super.onCleared()
    }
}