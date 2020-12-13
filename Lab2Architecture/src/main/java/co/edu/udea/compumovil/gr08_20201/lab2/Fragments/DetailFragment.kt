package co.edu.udea.compumovil.gr08_20201.lab2.Fragments

import android.content.Intent
import android.net.Uri
import android.text.Editable
import kotlinx.android.synthetic.main.fragment_detail.*



import android.os.Bundle
import android.os.Handler
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.Comment
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.R
import co.edu.udea.compumovil.gr08_20201.lab2.Service.CommentService
import co.edu.udea.compumovil.gr08_20201.lab2.Service.PoiService
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class DetailFragment : Fragment(){

    lateinit var adapter: CommentAdapter
    private var commentList: ArrayList<Comment> = ArrayList()
    private var disposable: Disposable? = null
    private var placeId:Int = 0
    private var uri="geo:"


    private val commentApiServe by lazy {
        CommentService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)


        setFragmentResultListener("requestKey"){
                key, bundle ->

            val result=bundle.getParcelable<POI>("place")

            if (result != null) {
                uri=uri.plus(result.lati).plus(",").plus(result.longi)
                placeId=result.id
                view.placeLabel.text = result.placeName
                descriptionInput.setText(result.description)
                temperatureInput.setText(result.temperature)
                generalInfoInput.setText(result.generalInfo)
                puntuationInput.setText(result.generalPuntuation.toString())
                locationInput.setText(result.location)

                Picasso.get()
                    .load(result.image)
                    .into(image)

            }else{
                Log.d("Places","No hay lugares")
            }


            getComments(placeId)
            Handler().postDelayed(
                {
                    adapter=CommentAdapter(commentList)
                    val recyclerView=view.recyclerComments
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                },
                1500
            )

             verMapa.setOnClickListener {
              showMap(Uri.parse(uri))
             }

        }


        return view
    }


    private fun getComments(poiId:Int) {
        disposable = commentApiServe.getCommentsPoi(poiId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { resultado ->
                    for(i: Int in resultado.indices){

                       var comment= Comment(resultado[i].id,resultado[i].comment,resultado[i].userPuntuation,resultado[i].poiId)
                        commentList.add(comment)

                    }

                },
                { error -> Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                    error.message?.let { Log.d("error", it) }
                }
            )

    }


    fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = geoLocation
        }
            startActivity(intent)
    }


}