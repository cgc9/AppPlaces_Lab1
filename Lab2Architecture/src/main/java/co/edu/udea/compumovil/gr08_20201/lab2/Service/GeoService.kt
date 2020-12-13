package co.edu.udea.compumovil.gr08_20201.lab2.Service

import co.edu.udea.compumovil.gr08_20201.lab2.Model.ModelGeo
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoService {

    @GET("json")
    fun getLocation(@Query("address") address: String,
                    @Query("key") key: String):
            Observable<ModelGeo.Result>

    companion object {
        fun create(): GeoService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .build()

            return retrofit.create(GeoService::class.java)
        }
    }
}