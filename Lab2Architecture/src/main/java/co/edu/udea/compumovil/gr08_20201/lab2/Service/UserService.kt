package co.edu.udea.compumovil.gr08_20201.lab2.Service

import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab2.Model.ModelUser
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {

    @GET("users")
    fun getUsers():
            Observable<List<ModelUser.Result>>

    @GET("users")
    fun getUserByEmail(@Query("email") email: String):
            Observable<ModelUser.Result>

    companion object {
        fun create(): UserService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://my-json-server.typicode.com/cgc9/Poi_CompuMovil/")
                .build()

            return retrofit.create(UserService::class.java)
        }
    }
}