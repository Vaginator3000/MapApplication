package com.template.mapapplication.ui.places

import com.template.models.Result
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.template.domain.repository.VisitedPlacesRepository
import com.template.models.VisitedPlaceModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PlacesViewModel() : ViewModel(), KoinComponent {

    private val spVisitedPlacesRepositoryImpl by inject<VisitedPlacesRepository>()

    fun getVisitedPlaces() = liveData<Result<List<VisitedPlaceModel>>> {
        emit(Result.Loading())
        val places = spVisitedPlacesRepositoryImpl.getAllPlaces()
        if (places == null) {
            emit(Result.Error())
        } else {
            emit(Result.Success(places))
        }
    }

    private fun startInBackground(foo: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            foo()
        }
    }

    fun removePlace(place : VisitedPlaceModel) {
        startInBackground {
            spVisitedPlacesRepositoryImpl.removePlace(place)
        }
    }

    fun removePlaceByIndex(index : Int) {
        startInBackground {
            spVisitedPlacesRepositoryImpl.removePlaceByIndex(index)
        }
    }
}