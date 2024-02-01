package com.myeong.prography.photos

import com.myeong.prography.domain.model.Photo
import kotlinx.collections.immutable.ImmutableList

/**
 * Created by MyeongKi.
 */
data class PhotoBookmarksUiState(
    val photos: ImmutableList<Photo>
)