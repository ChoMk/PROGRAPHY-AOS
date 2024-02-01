package com.myeong.prography.detail

import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */

sealed interface DetailUiState {
    data object Loading : DetailUiState

    data class HasData(
        val isBookmark: Boolean,
        val userName: String,
        val title: String,
        val description: String,
        val tags: ImmutableList<String>,
        val imageWidth: Int,
        val imageHeight: Int,
        val imageUrl: String,
    ) : DetailUiState
}