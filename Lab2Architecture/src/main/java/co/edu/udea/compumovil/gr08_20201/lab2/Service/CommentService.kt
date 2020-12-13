package co.edu.udea.compumovil.gr08_20201.lab2.Service

import co.edu.udea.compumovil.gr08_20201.lab2.Model.ModelComment
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface CommentService {
    @GET("comments")
    fun getComments():
            Observable<List<ModelComment.Result>>

    @GET("comments")
    fun getCommentsPoi(@Query("poiId") poiId: Int):
            Observable<List<ModelComment.Result>>

    companion object {
        fun create(): CommentService {

            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://my-json-server.typicode.com/cgc9/Poi_CompuMovil/")
                .build()

            return retrofit.create(CommentService::class.java)

        }

    }

}