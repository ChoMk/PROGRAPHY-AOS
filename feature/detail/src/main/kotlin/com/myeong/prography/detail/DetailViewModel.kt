package com.myeong.prography.detail

import ResultData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import asResult
import com.myeong.prography.detail.DetailIntent.Companion.toEvent
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import model.Photo
import usecase.LoadPhotoDetailUseCase

/**
 * Created by MyeongKi.
 */
class DetailViewModel(
    visibleSheetFlow: MutableSharedFlow<SheetEvent>,
    loadPhotoDetailUseCase: LoadPhotoDetailUseCase,
) : ViewModel() {
    private var viewModelState = DetailViewModelState()
    private val intentFlow = MutableSharedFlow<DetailIntent>()
    val intentInvoker: (DetailIntent) -> Unit = {
        viewModelScope.launch {
            intentFlow.emit(it)
        }
    }
    private val eventFlow = MutableSharedFlow<DetailEvent>()
    val eventInvoker: (DetailEvent) -> Unit = {
        viewModelScope.launch {
            eventFlow.emit(it)
        }
    }
    private val actionFlow = merge(
        eventFlow,
        intentFlow.map { it.toEvent() }
    )
    private val hideSheetFlow = actionFlow
        .filterIsInstance<DetailEvent.HideDetail>()
        .map { DetailViewModelState() }
        .onEach {
            visibleSheetFlow.emit(SheetEvent.HideSheet)
        }

    private val bookmarkFlow = actionFlow
        .filterIsInstance<DetailEvent.Bookmark>()
        .map {
            viewModelState
        }
    private val updateFlow = actionFlow
        .filterIsInstance<DetailEvent.UpdateDetail>()
        .transform { emitAll(loadPhotoDetailUseCase(it.photoId).asResult()) }
        .map {
            when (it) {
                is ResultData.Loading -> {
                    viewModelState.copy(
                        isLoading = true
                    )
                }

                is ResultData.Error -> {
                    viewModelState.copy(
                        isLoading = false
                    )
                }

                is ResultData.Success -> {
                    viewModelState.copy(
                        isLoading = false,
                        photo = it.data
                    )
                }
            }
        }

    val uiState = merge(
        updateFlow,
        bookmarkFlow,
        hideSheetFlow
    )
        .filterNotNull()
        .map { it.toUiState() }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.toUiState()
        )
    private fun DetailViewModelState.toUiState(): DetailUiState {
        viewModelState = this
        return if (this.isLoading) {
            DetailUiState.Loading
        } else {
            DetailUiState.HasData(
                isBookmark = bookmark,
                userName = photo?.userName ?: "",
                title = photo?.title ?: "",
                description = photo?.description ?: "",
                imageUrl = photo?.imageUrl?.full ?: "",
                imageWidth = photo?.imageWidth ?: 1,
                imageHeight = photo?.imageHeight ?: 1,
                tags = photo?.tags?.toImmutableList() ?: emptyList<String>().toImmutableList()
            )
        }

    }

    companion object {
        fun provideFactory(
            visibleSheetFlow: MutableSharedFlow<SheetEvent>,
            loadPhotoDetailUseCase: LoadPhotoDetailUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    visibleSheetFlow,
                    loadPhotoDetailUseCase,
                ) as T
            }
        }
    }
}

private data class DetailViewModelState(
    val photo: Photo? = null,
    val bookmark: Boolean = false,
    val isLoading: Boolean = false
)