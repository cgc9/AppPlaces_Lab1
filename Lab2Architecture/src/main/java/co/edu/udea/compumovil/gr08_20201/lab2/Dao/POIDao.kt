package co.edu.udea.compumovil.gr08_20201.lab2.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI

@Dao
interface POIDao {

        @Insert(onConflict = OnConflictStrategy.IGNORE)
        suspend fun addPoint(point: POI)

        @Query("DELETE FROM POI_table WHERE id = :pId")
        suspend fun deleteById( pId:Int)

        @Update
        suspend fun updatePlace(point:POI);

        @Delete
        suspend fun deletePlace(point:POI);

        @Query("SELECT * FROM POI_table ORDER BY id ASC")
        fun readAllPoints(): LiveData<List<POI>>

        @Query("SELECT COUNT (*) FROM POI_table")
        fun countData(): LiveData<Int>



}