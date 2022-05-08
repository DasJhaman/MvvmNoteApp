package com.task.noteapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : ViewEvent> : ViewModel() {
    private val disposables: CompositeDisposable = CompositeDisposable()
    private val eventChannel = Channel<T>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    protected fun emitViewEvent(event: T) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    fun addToDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}

open class ViewEvent