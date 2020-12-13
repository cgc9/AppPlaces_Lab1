package co.edu.udea.compumovil.gr08_20201.lab2.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.User
import co.edu.udea.compumovil.gr08_20201.lab2.R
import co.edu.udea.compumovil.gr08_20201.lab2.Service.GeoService
import co.edu.udea.compumovil.gr08_20201.lab2.Service.PoiService
import co.edu.udea.compumovil.gr08_20201.lab2.Service.UserService
import co.edu.udea.compumovil.gr08_20201.lab2.ViewModel.POIViewModel
import co.edu.udea.compumovil.gr08_20201.lab2.ViewModel.UserViewModel
import es.dmoral.toasty.Toasty
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_editplace.*
import kotlinx.android.synthetic.main.dialog_editplace.view.*
import kotlinx.android.synthetic.main.fragment_add_place.view.*
import kotlinx.android.synthetic.main.fragment_recycler_view.*
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class RecyclerViewFragment : Fragment(),PlaceAdapter.onClickListener {

    lateinit var poiViewModel: POIViewModel
    lateinit var adapter: PlaceAdapter
    private var placeList: ArrayList<POI> = ArrayList()
    private var disposable: Disposable? = null
    private var newPointId:Int=0
    var result= false
    var placesAdd: ArrayList<POI> = ArrayList()
    var placesAux: ArrayList<POI> = ArrayList()


    private val poiApiServe by lazy {
        PoiService.create()
    }

    var size:Int=0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        getActivity()?.setTitle(R.string.recyclerTitle)
        val view= inflater.inflate(R.layout.fragment_recycler_view, container, false)


        adapter=PlaceAdapter(this)
        val recyclerView=view.recyclerPlaces
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        showPlaces()

        view.addButton.setOnClickListener {
          //  setFragmentResult("requestKey", bundleOf("idNew" to newPointId))
            val transaction = fragmentManager?.beginTransaction()
            val fragment = AddPlaceFragment()
            transaction?.replace(R.id.mainFragment, fragment)
            transaction?.commit()
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        swipe.setOnRefreshListener {

            swipe.isRefreshing = false

        }
    }

    override fun onItemClick(item: POI, index: Int) {

       setFragmentResult("requestKey", bundleOf("place" to item))
        val transaction = fragmentManager?.beginTransaction()
        val fragment = DetailFragment()
        transaction?.replace(R.id.mainFragment, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit() }

    override fun onItemDelete(item: POI, index: Int) {
        val mDialog = AlertDialog.Builder(this.getView()?.context)
            .setTitle("Eliminar")
            .setMessage("¿Está seguro de que desea eliminar este lugar?")
            .setPositiveButton("Si",
                DialogInterface.OnClickListener { dialog, id ->
                    poiViewModel.deletePlace(item)
                    Toasty.success(requireContext(),"Lugar eliminado exitosamente",Toast.LENGTH_SHORT).show()
                    adapter.notifyItemRemoved(index)
                })

            .setNegativeButton("No") { dialog: DialogInterface?, which: Int ->
                dialog?.dismiss()
            }

        mDialog.show()
    }

    override fun onItemEdit(item: POI, index: Int) {
        //val context = this
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(item.placeName)
        val view = layoutInflater.inflate(R.layout.dialog_editplace, null)
        builder.setView(view);

        view.descriptionEditPlaces.setText(item.description)
        view.temperatureEditPlaces.setText(item.temperature)
        view.puntuationEditPlaces.setText(item.generalPuntuation.toString())
        view.locationEditPlaces.setText(item.location)
        view.generalInfoEditPlaces.setText(item.generalInfo)

        builder.setPositiveButton("Guardar",
            DialogInterface.OnClickListener { dialog, id ->

                var descriptionEdit= view.descriptionEditInputPlaces.editText?.text.toString()
                var temperatureEdit=view.temperatureEditInputPlaces.editText?.text.toString()
                var puntationEdit=(view.puntuationEditInputPlaces.editText?.text.toString()).toDouble()
                var locationEdit= view.locationEditInputPlaces.editText?.text.toString()
                var generalInfoEdit= view.generalInfoEditInputPlaces.editText?.text.toString()


                val editPlace=POI(item.id,item.placeName,descriptionEdit,generalInfoEdit, locationEdit,item.lati,item.longi, temperatureEdit,puntationEdit,item.image)
                poiViewModel.updatePlace(editPlace)
                adapter.notifyItemChanged(index)
                dialog?.dismiss()
                Toasty.success(requireContext(),"Lugar modificado exitosamente",Toast.LENGTH_SHORT).show()

            })

            .setNegativeButton("Cancelar") { dialog: DialogInterface?, which: Int ->
                dialog?.dismiss()
            }

      builder.show()

    }

    private fun getPlaces() {
        disposable = poiApiServe.getLugares()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    for(i: Int in result.indices){

                     var point= POI(0,result[i].placeName ,result[i].description,result[i].generalInfo, result[i].location,result[i].lati,result[i].longi,result[i].temperature,result[i].generalPuntuation,result[i].image)
                       placeList.add(point)
                        poiViewModel.addPoint(point)
                    }

                    adapter.setData(placeList)
                   },
                { error -> Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
                    error.message?.let { Log.d("error", it) }
                }
            )
    }

    private fun showPlaces(){
        poiViewModel = ViewModelProvider(this).get(POIViewModel::class.java)
        poiViewModel.count.observe(viewLifecycleOwner, Observer { cont->
            newPointId = cont
            if(cont==0){

                getPlaces()

            }
            else{

                poiViewModel.readAllPoints.observe(viewLifecycleOwner, Observer { point ->
                    adapter.setData(point)

                })

            }
        })

    }

    private fun updateData() {
    }


}




