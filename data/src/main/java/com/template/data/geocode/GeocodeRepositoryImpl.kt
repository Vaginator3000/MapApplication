package com.template.data.geocode

import com.template.data.retrofit.Common
import com.template.domain.geocode.GeocodeRepository
import com.template.models.GeoResponseModel

class GeocodeRepositoryImpl : GeocodeRepository {
    override suspend fun getAddress(key : String, geocode : String) : GeoResponseModel {
        return Common.apiServises.getAddress(key = key, geocode = geocode)
    }
}