package co.edu.udea.compumovil.gr08_20201.lab2.Repositories

import androidx.lifecycle.LiveData
import co.edu.udea.compumovil.gr08_20201.lab2.Dao.CityDao
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.City



class CityRepository(private val cityDao: CityDao)  {
    val readAllCities: LiveData<List<City>> = cityDao.readAllCities()
    lateinit var readCity: LiveData<City>

    suspend fun addCity(city: City){
        cityDao.addCity(city)
    }
}