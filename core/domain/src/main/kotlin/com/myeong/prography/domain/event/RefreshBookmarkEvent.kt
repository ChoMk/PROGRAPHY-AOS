package com.myeong.prography.domain.event

import com.myeong.prography.domain.model.Photo

/**
 * Created by MyeongKi.
 */
sealed interface RefreshBookmarkEvent {
    data class AddBookmark(val photo: Photo) : RefreshBookmarkEvent
    data class DeleteBookmark(val photoId: String) : RefreshBookmarkEvent
}