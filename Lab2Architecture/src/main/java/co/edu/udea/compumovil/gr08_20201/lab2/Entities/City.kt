package co.edu.udea.compumovil.gr08_20201.lab2.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var cityName: String

)


