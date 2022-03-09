package com.template.models

sealed class Result<out T> {
    class Success<out T>(val value: T? = null): Result<T>()
    class Loading<out T>: Result<T>()
    class Error(val msg: String? = null, val cause: Exception? = null): Result<Nothing>()
}