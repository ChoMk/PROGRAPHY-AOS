package com.myeong.prography.network.photo

import com.myeong.prography.network.UNSPLASH_HOST
import com.myeong.prography.network.photo.result.PhotoResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.myeong.prography.domain.model.Photo
import com.myeong.prography.domain.model.PhotoImageUrl
import com.myeong.prography.domain.source.request.LoadPhotosOption

/**
 * Created by MyeongKi.
 */
class PhotoHttpClient(private val httpClient: HttpClient) {
    suspend fun requestPhotos(requestOption: LoadPhotosOption): List<PhotoResponse> {
        return httpClient.get("$HOST/photos") {
            parameter("page", requestOption.page)
            parameter("per_page", requestOption.pageSize)
        }.body()
    }

    suspend fun requestPhoto(photoId: String): PhotoResponse {
        return httpClient.get("$HOST/photos/${photoId}").body()
    }
    suspend fun requestRandomPhotos(): List<PhotoResponse> {
        return httpClient.get("$HOST/photos/random").body()
    }

    companion object {
        private const val HOST = UNSPLASH_HOST
    }
}

fun PhotoResponse.toPhoto(): Photo {
    return Photo(
        id = id,
        userName = user.username,
        imageHeight = height,
        imageWidth = width,
        imageUrl = PhotoImageUrl(
            full = urls.full,
            small = urls.small
        ),
        description = description,
        title = user.name,
        tags = tags.map { it.title },
        isBookmark = false,
    )
}