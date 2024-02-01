package com.myeong.prography.holder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myeong.prography.holder.HolderIntent.Companion.toEvent
import com.myeong.prography.holder.sheet.HolderSheetType
import com.myeong.prography.holder.sheet.SheetEvent
import kotlinx.coroutines.flow.Flow
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
class HolderViewModel(
    visibleSheetFlow: Flow<SheetEvent>
) : ViewModel() {
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
        intentFlow.map { it.toEvent() },
        visibleSheetFlow.map { it.toEvent() }
    )
    private val navigateComponentFlow = actionFlow
        .filterIsInstance<HolderEvent.NavigateComponent>()
        .filter { uiState.value.selectedComponent != it.component }
        .map {
            uiState.value.copy(
                selectedComponent = it.component
            )
        }
    private val hideSheetFlow = actionFlow
        .filterIsInstance<HolderEvent.HideSheet>()
        .map {
            uiState.value.copy(
                holderSheetType = HolderSheetType.None
            )
        }
    private val showDetailSheetFlow = actionFlow
        .filterIsInstance<HolderEvent.ShowDetailSheet>()
        .map {
            uiState.value.copy(
                holderSheetType = HolderSheetType.Detail
            )
        }
    val uiState: StateFlow<HolderUiState> = merge(
        navigateComponentFlow,
        hideSheetFlow,
        showDetailSheetFlow
    )
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            HolderUiState(
                selectedComponent = HolderComponent.PHOTOS,
                holderSheetType = HolderSheetType.None
            )
        )

    private fun SheetEvent.toEvent(): HolderEvent {
        return when (this) {
            is SheetEvent.HideSheet -> {
                HolderEvent.HideSheet
            }

            is SheetEvent.ShowDetailSheet -> {
                HolderEvent.ShowDetailSheet
            }
        }
    }

    companion object {
        fun provideFactory(
            visibleSheetFlow: Flow<SheetEvent>
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HolderViewModel(
                    visibleSheetFlow
                ) as T
            }
        }
    }
}