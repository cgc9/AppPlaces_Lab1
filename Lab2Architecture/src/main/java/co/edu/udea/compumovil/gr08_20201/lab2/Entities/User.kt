package co.edu.udea.compumovil.gr08_20201.lab2.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val password:String
)