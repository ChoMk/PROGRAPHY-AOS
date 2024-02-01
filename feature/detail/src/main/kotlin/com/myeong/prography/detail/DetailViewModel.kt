package com.myeong.prography.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myeong.prography.detail.DetailIntent.Companion.toEvent
import com.myeong.prography.domain.ResultData
import com.myeong.prography.domain.asResult
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.usecase.AddPhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.DeletePhotoBookmarkUseCase
import com.myeong.prography.domain.usecase.LoadPhotoDetailUseCase
import com.myeong.prography.ui.event.SheetEvent
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

/**
 * Created by MyeongKi.
 */
class DetailViewModel(
    visibleSheetFlow: MutableSharedFlow<SheetEvent>,
    loadPhotoDetailUseCase: LoadPhotoDetailUseCase,
    addPhotoBookmarkUseCase: AddPhotoBookmarkUseCase,
    deletePhotoBookmarkUseCase: DeletePhotoBookmarkUseCase,
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

    private val addBookmarkFlow = actionFlow
        .filterIsInstance<DetailEvent.Bookmark>()
        .filter { (viewModelState.photo?.isBookmark ?: false).not() }
        .transform {
            emitAll(addPhotoBookmarkUseCase(viewModelState.photo!!).asResult())
        }
        .map {
            when (it) {
                is ResultData.Loading, is ResultData.Error -> {
                    null
                }

                is ResultData.Success -> {
                    viewModelState.copy(
                        photo = viewModelState.photo?.copy(
                            isBookmark = true
                        ),
                    )
                }
            }
        }
        .onEach {
            //notify outside
        }
    private val deleteBookmarkFlow = actionFlow
        .filterIsInstance<DetailEvent.Bookmark>()
        .filter { (viewModelState.photo?.isBookmark ?: false) }
        .transform {
            emitAll(deletePhotoBookmarkUseCase(viewModelState.photo!!.id).asResult())
        }
        .map {
            when (it) {
                is ResultData.Loading, is ResultData.Error -> {
                    null
                }

                is ResultData.Success -> {
                    viewModelState.copy(
                        photo = viewModelState.photo?.copy(
                            isBookmark = false
                        ),
                    )
                }
            }
        }
        .onEach {
            //notify outside
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
        addBookmarkFlow,
        deleteBookmarkFlow,
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
                isBookmark = photo?.isBookmark ?: false,
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
            addPhotoBookmarkUseCase: AddPhotoBookmarkUseCase,
            deletePhotoBookmarkUseCase: DeletePhotoBookmarkUseCase,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(
                    visibleSheetFlow,
                    loadPhotoDetailUseCase,
                    addPhotoBookmarkUseCase,
                    deletePhotoBookmarkUseCase
                ) as T
            }
        }
    }
}

private data class DetailViewModelState(
    val photo: Photo? = null,
    val isLoading: Boolean = false
)