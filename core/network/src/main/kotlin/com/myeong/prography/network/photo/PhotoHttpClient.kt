package com.myeong.prography.network.photo

import com.myeong.prography.network.UNSPLASH_HOST
import com.myeong.prography.network.photo.result.PhotoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class PhotoHttpClient(private val httpClient: HttpClient) {
    suspend fun requestPhotos(requestOption: LoadPhotosOption):List<PhotoResponse> {
        return httpClient.get("$HOST/photos") {
            parameter("page", requestOption.page)
            parameter("per_page", requestOption.pageSize)
        }.body()
    }
    companion object {
        private const val HOST = UNSPLASH_HOST
    }
}