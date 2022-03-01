package com.template.models

sealed class Result<out T: Any> {
    data class Success<out T: Any>(val value: T? = null): Result<T>()
    data class Error(val msg: String? = null, val cause: Exception? = null): Result<Nothing>()
}