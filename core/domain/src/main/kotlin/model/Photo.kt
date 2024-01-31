package model

/**
 * Created by MyeongKi.
 */
data class Photo(
    val userName: String,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val title: String,
    val description: String,
)