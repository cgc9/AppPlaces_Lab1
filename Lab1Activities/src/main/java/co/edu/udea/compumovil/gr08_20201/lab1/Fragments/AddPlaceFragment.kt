package co.edu.udea.compumovil.gr08_20201.lab1.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.R
import co.edu.udea.compumovil.gr08_20201.lab1.ViewModel.POIViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_add_place.*
import kotlinx.android.synthetic.main.fragment_add_place.view.*


class AddPlaceFragment : Fragment() {

    lateinit var poiViewModel: POIViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getActivity()?.setTitle(R.string.addPlace)

        val view= inflater.inflate(R.layout.fragment_add_place, container, false)
        poiViewModel = ViewModelProvider(this).get(POIViewModel::class.java)
        view.addPlaceButton.setOnClickListener {
            insertPointToDatabase()
        }

        view.cancelButton.setOnClickListener{
            val transaction= getParentFragmentManager().beginTransaction()
            val fragment = RecyclerViewFragment()
            transaction.replace(R.id.mainFragment,fragment)
            transaction.commit()

        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val transaction= getParentFragmentManager().beginTransaction()
            val fragment = RecyclerViewFragment()
            transaction.replace(R.id.mainFragment,fragment)
            transaction.commit()
        }



        return view
    }

    private fun insertPointToDatabase() {
        val namePlace = placeNameEdit.editText?.text.toString()
        val description = descriptionEdit.editText?.text.toString()
        val generalInfo = generalInfoEdit.editText?.text.toString()
        val location = locationEdit.editText?.text.toString()
        val temperature = temperatureEdit.editText?.text.toString()
        val puntuation=(puntuacionEdit.editText?.text.toString()).toDouble()


        if(inputCheck(namePlace ,description,generalInfo, location, temperature,puntuation)){
            var point= POI(0,namePlace ,description,generalInfo, location, temperature,puntuation,"")
            poiViewModel.addPoint(point)
            Toasty.success(requireContext(), "Registro  exitoso", Toast.LENGTH_SHORT).show()
            val transaction= getParentFragmentManager().beginTransaction()
            val fragment = RecyclerViewFragment()
            transaction.replace(R.id.mainFragment,fragment)
            transaction.commit()

        }else{
            Toasty.error(requireContext(), "Por favor ingresar todos los campos", Toast.LENGTH_SHORT).show()

        }

    }

    private fun inputCheck (
        namePlace:String,
        description: String,
        generalInfo:String, location:String, temperature:String,
        puntuation: Double
    ) : Boolean {

        return !(TextUtils.isEmpty(namePlace)||TextUtils.isEmpty(description)||TextUtils.isEmpty(generalInfo)|| TextUtils.isEmpty( location)|| TextUtils.isEmpty( temperature)|| TextUtils.isEmpty(
            puntuation.toString()
        ))

    }

}