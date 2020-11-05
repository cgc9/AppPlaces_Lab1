package co.edu.udea.compumovil.gr08_20201.lab1.Fragments

import android.text.Editable
import kotlinx.android.synthetic.main.fragment_detail.*



import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.fragment_detail.view.*

class DetailFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_detail, container, false)
        setFragmentResultListener("requestKey"){
                key, bundle ->

            val result=bundle.getParcelable<POI>("place")
            if (result != null) {
                view.placeLabel.text = result.placeName
                descriptionInput.setText(result.description)
                temperatureInput.setText(result.temperature)
                generalInfoInput.setText(result.generalInfo)
                puntuationInput.setText(result.puntuation.toString())
                locationInput.setText(result.location)

                var imgResource:Int

                when(result.id){
                    1->  imgResource = R.drawable.lanikai;
                    2->  imgResource = R.drawable.arizona_memorial;
                    3->  imgResource = R.drawable.waikiki;
                    4->  imgResource = R.drawable.valle;
                    5->  imgResource = R.drawable.haleakala;
                    else -> { imgResource=R.drawable.def
                    }
                }
                image.setImageResource(imgResource)
            }else{
                Log.d("Places","No hay lugares")
            }

        }

        return view
    }






}