package com.myeong.prography.network.photo.result

import kotlinx.serialization.Serializable

/**
 * Created by MyeongKi.
 */
@Serializable
data class PhotoResponse(
    val user: UserResponse = UserResponse(),
    val width: Int = 0,
    val height: Int = 0,
    val description: String = "",
    val urls: UrlResponse = UrlResponse()
)

@Serializable
data class UserResponse(
    val username: String = "",
    val name: String = ""
)

@Serializable
data class UrlResponse(
    val full: String = "",
    val small: String = "",
)