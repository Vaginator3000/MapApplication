package com.template.data.retrofit

import com.template.models.GeoResponseModel
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("1.x/?")
    suspend fun getAddress(@Query("apikey") key : String,
                           @Query("format") format : String = "json",
                           @Query("geocode") geocode : String) : GeoResponseModel
}