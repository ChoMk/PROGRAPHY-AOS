package com.myeong.prography.photos

import com.myeong.prography.domain.model.Photo

/**
 * Created by MyeongKi.
 */
sealed interface PhotosIntent {
    data class ClickPhoto(val photo: Photo) : PhotosIntent
    companion object {
        fun PhotosIntent.toEvent(): PhotosEvent {
            return when (this) {
                is ClickPhoto -> {
                    PhotosEvent.ShowDetailSheet(photoId = photo.id)
                }
            }
        }
    }
}

sealed interface PhotosEvent {
    data class ShowDetailSheet(val photoId: String) : PhotosEvent
    data object LoadPhotoBookmarks : PhotosEvent
    data class AddBookmarkItem(val photo: Photo) : PhotosEvent
    data class DeleteBookmarkItem(val photoId: String) : PhotosEvent
}