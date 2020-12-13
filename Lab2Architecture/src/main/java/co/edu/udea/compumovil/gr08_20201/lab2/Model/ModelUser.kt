package co.edu.udea.compumovil.gr08_20201.lab2.Model

import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User

object ModelUser {
    data class Result(val id: Int,
                      val name: String,
                      val lastName: String,
                      val email: String,
                      val password:String)
}