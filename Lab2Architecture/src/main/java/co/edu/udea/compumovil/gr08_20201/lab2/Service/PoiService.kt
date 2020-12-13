package co.edu.udea.compumovil.gr08_20201.lab2.Service

import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab2.Model.ModelPoi
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PoiService {
    @GET("poi")
    fun getLugares(): Observable<List<ModelPoi.Result>>

    @GET("poi")
    fun getPlace(@Query("id") id: Int):
            Observable<User>

    companion object {
        fun create(): PoiService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://my-json-server.typicode.com/cgc9/Poi_CompuMovil/")
                .build()

            return retrofit.create(PoiService::class.java)
        }
    }
}