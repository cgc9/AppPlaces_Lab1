package co.edu.udea.compumovil.gr08_20201.lab1.Entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "POI_table")
data class  POI(
    @PrimaryKey(autoGenerate = true) var id:Int,
    var placeName: String,
    var description: String,
    var generalInfo: String,
    var location:String,
    var temperature: String,
    var puntuation: Double,
    val image: String
): Parcelable