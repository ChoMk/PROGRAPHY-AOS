package com.myeong.prography.domain.model

/**
 * Created by MyeongKi.
 */
data class Photo(
    val id: String,
    val userName: String,
    val imageUrl: PhotoImageUrl,
    val imageWidth: Int,
    val imageHeight: Int,
    val title: String,
    val description: String,
    val tags: List<String>,
    val isBookmark: Boolean
)

data class PhotoImageUrl(
    val full: String,
    val small: String
)