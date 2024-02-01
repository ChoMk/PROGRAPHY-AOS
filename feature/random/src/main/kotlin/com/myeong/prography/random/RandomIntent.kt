package com.myeong.prography.random

/**
 * Created by MyeongKi.
 */
sealed interface RandomIntent {
    data object ClickInfo : RandomIntent
    data object ToSwipeLeft : RandomIntent
    data object ToSwipeRight : RandomIntent
    companion object {
        fun RandomIntent.toEvent(): RandomEvent {
            return when (this) {

                is ToSwipeLeft -> {
                    RandomEvent.PopPhoto
                }

                is ToSwipeRight -> {
                    RandomEvent.AddBookmarkAndPopPhoto
                }

                is ClickInfo -> {
                    RandomEvent.ShowDetailSheet
                }
            }
        }
    }
}

sealed interface RandomEvent {
    data object PopPhoto : RandomEvent
    data object AddBookmarkAndPopPhoto : RandomEvent
    data object ShowDetailSheet : RandomEvent
    data object LoadRandomPhotos : RandomEvent
}