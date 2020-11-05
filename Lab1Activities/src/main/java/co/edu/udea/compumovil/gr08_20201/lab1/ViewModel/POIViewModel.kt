package co.edu.udea.compumovil.gr08_20201.lab1.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import co.edu.udea.compumovil.gr08_20201.lab1.Database.AppDatabase
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.Repositories.POIRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class POIViewModel(application: Application): AndroidViewModel(application) {

    val count: LiveData<Int>
    val repository: POIRepository
    val readAllPoints: LiveData<List<POI>>


    init {
        val pointDao = AppDatabase.getDatabase(application).pointDao()
        repository = POIRepository(pointDao)
        readAllPoints = repository.readAllPoints
        count= repository.count

    }

    fun addPoint(point: POI){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addPoint(point)
        }
    }

    fun deletePlace(point:POI){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePlace(point)
        }
    }

    fun updatePlace(point:POI){
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(point)
        }
    }



}