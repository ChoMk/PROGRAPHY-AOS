package com.myeong.prography.domain.source.request

/**
 * Created by MyeongKi.
 */
data class LoadPhotosOption(
    val page: Int = 1,
    val pageSize: Int = 10,
)