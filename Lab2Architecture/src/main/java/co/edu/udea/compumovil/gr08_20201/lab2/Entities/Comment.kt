package co.edu.udea.compumovil.gr08_20201.lab2.Entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "comment_table", foreignKeys = arrayOf(ForeignKey(entity = POI::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("poiId"),
    onDelete = ForeignKey.NO_ACTION)))


data class Comment(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var comment: String,
    var userPuntuation: Double,
    var poiId:Int

)