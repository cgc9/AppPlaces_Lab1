package co.edu.udea.compumovil.gr08_20201.lab2.Entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "poi_table")
data class  POI(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var placeName: String,
    var description: String,
    var generalInfo: String,
    var location:String,
    var lati:String,
    var longi:String,
    var temperature: String,
    var generalPuntuation: Double,
    var image: String
) : Parcelable