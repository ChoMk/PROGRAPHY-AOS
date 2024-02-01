package com.myeong.prography.domain/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/**
 * Created by MyeongKi.
 */
sealed interface ResultData<out T> {
    data class Success<T>(val data: T) : ResultData<T>
    data class Error(val exception: Throwable? = null) : ResultData<Nothing>
    data object Loading : ResultData<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ResultData<T>> {
    return this
        .map<T, ResultData<T>> {
            ResultData.Success(it)
        }
        .onStart { emit(ResultData.Loading) }
        .catch { emit(ResultData.Error(it)) }
}