package com.myeong.prography.holder

/**
 * Created by MyeongKi.
 */
sealed interface HolderIntent {
    data object ClickPhotos : HolderIntent
    data object ClickRandom : HolderIntent
    companion object {
        fun HolderIntent.toEvent(): HolderEvent {
            return when (this) {
                is ClickPhotos -> {
                    HolderEvent.NavigateComponent(component = HolderComponent.PHOTOS)
                }

                is ClickRandom -> {
                    HolderEvent.NavigateComponent(component = HolderComponent.RANDOM)
                }
            }
        }
    }
}

sealed interface HolderEvent {
    data class NavigateComponent(val component: HolderComponent) : HolderEvent
    data object HideSheet : HolderEvent
    data object ShowDetailSheet : HolderEvent
}