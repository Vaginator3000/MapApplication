package com.template.domain.geocode

import com.template.models.GeoResponseModel

interface GeocodeRepository {
    suspend fun getAddress(key : String, geocode : String) : GeoResponseModel
}