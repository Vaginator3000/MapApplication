package com.template.data.db

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.models.VisitedPlaceModel

class SharedPrefsVisitedPlacesDB (context : Context) {
    private val sPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun getAllVisitedPlaces(): List<VisitedPlaceModel>? {
        val listInStr = sPrefs.getString("VisitedPlaceModels", null) ?: return null

        val type = object : TypeToken<List<VisitedPlaceModel>>() {}.type
        val goods: List<VisitedPlaceModel> = gson.fromJson(listInStr, type)
        return goods as MutableList<VisitedPlaceModel>
    }

    fun savePlaces(goods: List<VisitedPlaceModel>) {
        sPrefs.edit()
            .putString("VisitedPlaceModels", gson.toJson(goods))
            .apply()
    }

    fun addOrUpdatePlace(place: VisitedPlaceModel) {
        val allPlaces = getAllVisitedPlaces()?.toMutableList()
        if (allPlaces == null) {
            savePlaces(listOf(place))
            return
        }
        val indexOfThatPlace = allPlaces.map { it.address }.indexOf(place.address)
        //если нашел такой адрес, то удаляем его
        if (indexOfThatPlace != -1) {
            allPlaces.removeAt(indexOfThatPlace)
        }
        //а новый адресс (или такой же, но с новым временем) добавили в конец
        allPlaces.add(place)
        savePlaces(allPlaces)
    }

    fun removePlace(place: VisitedPlaceModel) {
        val allPlaces = getAllVisitedPlaces()?.toMutableList() ?: return
        allPlaces.remove(place)
        savePlaces(allPlaces)
    }

    fun removePlaceByIndex(index: Int) {
        val allPlaces = getAllVisitedPlaces()?.toMutableList() ?: return
        allPlaces.removeAt(index)
        savePlaces(allPlaces)
    }
}