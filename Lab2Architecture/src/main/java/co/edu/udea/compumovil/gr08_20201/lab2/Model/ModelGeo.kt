package co.edu.udea.compumovil.gr08_20201.lab2.Model

object ModelGeo {
    data class Result(val results:ArrayList<Results>)
    data class Results(val geometry:Geometry)
    data class Geometry(val location:Location)
    data class Location(val lat:String,val lng:String)

}