package com.myeong.prography.detail

/**
 * Created by MyeongKi.
 */
sealed interface DetailIntent {
    data object ClickClose : DetailIntent
    data object ClickBookmark : DetailIntent
    companion object {
        fun DetailIntent.toEvent(): DetailEvent {
            return when (this) {
                is ClickClose -> {
                    DetailEvent.HideDetail
                }

                is ClickBookmark -> {
                    DetailEvent.Bookmark
                }
            }
        }
    }
}

sealed interface DetailEvent {
    data object HideDetail : DetailEvent
    data object Bookmark : DetailEvent
    data class UpdateDetail(val photoId: String) : DetailEvent
}
