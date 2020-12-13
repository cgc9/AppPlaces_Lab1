package co.edu.udea.compumovil.gr08_20201.lab2.Model

import co.edu.udea.compumovil.gr08_20201.lab2.Entities.Comment
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI

object ModelComment {
    data class Result(var id: Int,
                      var comment: String,
                      var userPuntuation: Double,
                      var poiId:Int)
}