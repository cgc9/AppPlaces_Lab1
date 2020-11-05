package co.edu.udea.compumovil.gr08_20201.lab1.Repositories

import androidx.lifecycle.LiveData
import co.edu.udea.compumovil.gr08_20201.lab1.Dao.POIDao
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI

class POIRepository(private val pointDao: POIDao) {

    val readAllPoints: LiveData<List<POI>> = pointDao.readAllPoints()
    val count: LiveData<Int> = pointDao.countData()
    suspend fun addPoint(point: POI){
        pointDao.addPoint(point)
    }

    suspend fun deletePlace(point: POI){
        pointDao.deletePlace(point)
    }

    suspend fun update(point: POI){
        pointDao.updatePlace(point)
    }





}