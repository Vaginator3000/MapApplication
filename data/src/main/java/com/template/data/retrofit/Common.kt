package com.template.data.retrofit

object Common {
    private const val BASE_URL = "https://geocode-maps.yandex.ru/"
    // private const val BASE_URL = "https://api.geotree.ru/"

    val apiServises : ApiService
        get() = RetrofitBuilder.getClient(BASE_URL).create(ApiService::class.java)
}