package model

/**
 * Created by MyeongKi.
 */
data class Photo(
    val userName: String,
    val imageUrl: PhotoImageUrl,
    val imageWidth: Int,
    val imageHeight: Int,
    val title: String,
    val description: String,
)

data class PhotoImageUrl(
    val full: String,
    val small: String
)