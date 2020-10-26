package co.edu.udea.compumovil.gr08_20201.lab1.Entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "POI_table")
data class  POI(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val placeName: String,
    val description: String,
    val generalInfo: String,
    val location:String,
    val temperature: String,
    val puntuation: Double,
    val image: String
): Parcelable