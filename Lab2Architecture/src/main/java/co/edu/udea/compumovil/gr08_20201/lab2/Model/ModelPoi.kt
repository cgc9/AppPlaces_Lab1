package co.edu.udea.compumovil.gr08_20201.lab2.Model

import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI

object ModelPoi {
    data class Result( var id:Int,
                       var placeName: String,
                       var description: String,
                       var generalInfo: String,
                       var location:String,
                       var lati:String,
                       var longi:String,
                       var temperature: String,
                       var generalPuntuation: Double,
                       var image: String)

}