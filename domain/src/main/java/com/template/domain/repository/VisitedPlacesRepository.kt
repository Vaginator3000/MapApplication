package com.template.domain.repository

import com.template.models.VisitedPlaceModel

interface VisitedPlacesRepository {
    suspend fun addOrUpdatePlace(place: VisitedPlaceModel)
    suspend fun removePlace(place: VisitedPlaceModel)
    suspend fun removePlaceByIndex(index: Int)
    suspend fun getAllPlaces() : List<VisitedPlaceModel>?
}