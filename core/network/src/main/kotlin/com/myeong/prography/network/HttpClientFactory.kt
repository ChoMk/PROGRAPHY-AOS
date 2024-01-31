package com.myeong.prography.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Created by MyeongKi.
 */
object HttpClientFactory {
    fun createUnsplashHttpClient(): HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
            )
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
        }
        defaultRequest {
            header(HttpHeaders.Authorization, "Client-ID $UNSPLASH_ACCESS_KEY")
        }
        HttpResponseValidator {
            validateResponse { response ->
                when (val statusCode = response.status.value) {
                    in 400..499 -> {
                        throw ClientRequestException(response, "Client error with status code $statusCode")
                    }
                    in 500..599 -> {
                        throw ServerResponseException(response, "Server error with status code $statusCode")
                    }
                    else -> Unit
                }
            }
        }
    }
}