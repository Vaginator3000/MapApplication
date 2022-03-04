package com.template.data.repository

import android.content.Context
import com.template.data.db.SharedPrefsVisitedPlacesDB
import com.template.domain.repository.VisitedPlacesRepository
import com.template.models.VisitedPlaceModel

class SharedPrefsVisitedPlacesRepositoryImpl(context : Context) : VisitedPlacesRepository {
    private val sharedPrefsVisitedPlacesDB = SharedPrefsVisitedPlacesDB(context)

    override suspend fun addOrUpdatePlace(place: VisitedPlaceModel) {
        sharedPrefsVisitedPlacesDB.addOrUpdatePlace(place)
    }

    override suspend fun removePlace(place: VisitedPlaceModel) {
        sharedPrefsVisitedPlacesDB.removePlace(place)
    }

    override suspend fun removePlaceByIndex(index: Int) {
        sharedPrefsVisitedPlacesDB.removePlaceByIndex(index)
    }

    override suspend fun getAllPlaces(): List<VisitedPlaceModel>? {
        return sharedPrefsVisitedPlacesDB.getAllVisitedPlaces()
    }
}