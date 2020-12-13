package co.edu.udea.compumovil.gr08_20201.lab2.Fragments

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.R
import co.edu.udea.compumovil.gr08_20201.lab2.Service.GeoService
import co.edu.udea.compumovil.gr08_20201.lab2.ViewModel.POIViewModel
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_add_place.*
import kotlinx.android.synthetic.main.fragment_add_place.view.*


class AddPlaceFragment : Fragment() {

    lateinit var poiViewModel: POIViewModel
    private var key: String="AIzaSyBQUEM9lItoxsh5682YP5w7p_4ArDb9Fws"
    private  var latitud:String=""
    private var longitud:String=""
    private var disposable: Disposable? = null
    var newUser=false

    private val GeoApiServe by lazy {
        GeoService.create()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getActivity()?.setTitle(R.string.addPlace)

        val view= inflater.inflate(R.layout.fragment_add_place, container, false)
        poiViewModel = ViewModelProvider(this).get(POIViewModel::class.java)


        view.selectImage.setOnClickListener {

        }


        view.addPlaceButton.setOnClickListener {
            insertPointToDatabase()
        }

        view.cancelButton.setOnClickListener{
            val transaction= getParentFragmentManager().beginTransaction()
            val fragment = RecyclerViewFragment()
            setFragmentResult("requestKey", bundleOf("newUser" to newUser))
            transaction.replace(R.id.mainFragment, fragment)
            transaction.commit()

        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val transaction= getParentFragmentManager().beginTransaction()
            val fragment = RecyclerViewFragment()
            transaction.replace(R.id.mainFragment, fragment)
            transaction.commit()
        }



        return view
    }

    private fun insertPointToDatabase() {
        val namePlace = placeNameEdit.editText?.text.toString()
        val description = descriptionEdit.editText?.text.toString()
        val generalInfo = generalInfoEdit.editText?.text.toString()
        val location = locationEdit.editText?.text.toString()
        searchCoor(namePlace)


        val temperature = temperatureEdit.editText?.text.toString()
        val puntuation=(puntuacionEdit.editText?.text.toString()).toDouble()

        Handler().postDelayed(Runnable {

            Log.d("latitud", latitud)
            Log.d("Longitud", longitud)

            if (inputCheck(
                    namePlace,
                    description,
                    generalInfo,
                    location,
                    temperature,
                    puntuation
                )
            ) {
                newUser = true
                var point = POI(
                    0,
                    namePlace,
                    description,
                    generalInfo,
                    location,
                    latitud,
                    longitud,
                    temperature,
                    puntuation,
                    "https://media-cdn.tripadvisor.com/media/photo-s/1a/dd/6a/f0/paradise-taveuni.jpg"
                )
                poiViewModel.addPoint(point)
                Toasty.success(requireContext(), "Registro  exitoso", Toast.LENGTH_SHORT).show()
                setFragmentResult("requestKey", bundleOf("newUser" to newUser))

                val transaction = getParentFragmentManager().beginTransaction()
                val fragment = RecyclerViewFragment()
                transaction.replace(R.id.mainFragment, fragment)
                transaction.commit()

            } else if (!inputCheck(
                    namePlace,
                    description,
                    generalInfo,
                    location,
                    temperature,
                    puntuation
                )
            ) {

                Toasty.error(
                    requireContext(),
                    "Por favor ingresar todos los campos",
                    Toast.LENGTH_SHORT
                ).show()
                setFragmentResult("requestKey", bundleOf("newUser" to newUser))

            }
        }, 1500)



    }

    private fun inputCheck(
        namePlace: String,
        description: String,
        generalInfo: String, location: String, temperature: String,
        puntuation: Double
    ) : Boolean {

        return !(TextUtils.isEmpty(namePlace)||TextUtils.isEmpty(description)||TextUtils.isEmpty(
            generalInfo
        )|| TextUtils.isEmpty(location)|| TextUtils.isEmpty(temperature)|| TextUtils.isEmpty(
            puntuation.toString()
        ))

    }

    private fun searchCoor(location: String){
        disposable=GeoApiServe.getLocation(
            location,
            key
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    latitud = result.results[0].geometry.location.lat
                    longitud = result.results[0].geometry.location.lng
                    Log.d("Latitu", result.results[0].geometry.location.lat)
                    Log.d("Long", result.results[0].geometry.location.lng)
                },
                { error ->
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                    error.message?.let { Log.d("error", it) }
                }
            )

    }


}