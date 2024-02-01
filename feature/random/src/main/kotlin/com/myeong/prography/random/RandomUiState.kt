package com.myeong.prography.random

import com.myeong.prography.domain.model.Photo
import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */
data class RandomUiState(
    val photos: ImmutableList<Photo>
)