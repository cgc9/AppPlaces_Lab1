package co.edu.udea.compumovil.gr08_20201.lab2.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.City
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI

@Dao
interface CityDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCity(city: City)

    @Query("SELECT * FROM CITY_TABLE ORDER BY id ASC")
    fun readAllCities(): LiveData<List<City>>
}