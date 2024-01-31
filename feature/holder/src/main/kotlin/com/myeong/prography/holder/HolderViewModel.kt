package com.myeong.prography.holder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myeong.prography.holder.HolderIntent.Companion.toEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
class HolderViewModel : ViewModel() {
    private val intentFlow = MutableSharedFlow<HolderIntent>()
    val intentInvoker: (HolderIntent) -> Unit = {
        viewModelScope.launch {
            intentFlow.emit(it)
        }
    }
    private val eventFlow = MutableSharedFlow<HolderEvent>()
    val eventInvoker: (HolderEvent) -> Unit = {
        viewModelScope.launch {
            eventFlow.emit(it)
        }
    }
    private val actionFlow = merge(
        eventFlow,
        intentFlow.map { it.toEvent() }
    )
    private val navigateComponentFlow = actionFlow
        .filterIsInstance<HolderEvent.NavigateComponent>()
        .filter { uiState.value.selectedComponent != it.component }
        .map {
            uiState.value.copy(
                selectedComponent = it.component
            )
        }

    val uiState: StateFlow<HolderUiState> = merge(
        navigateComponentFlow,
    )
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HolderUiState(
                selectedComponent = HolderComponent.PHOTOS
            )
        )
}