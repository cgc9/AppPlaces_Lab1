package co.edu.udea.compumovil.gr08_20201.lab1.Fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.udea.compumovil.gr08_20201.lab1.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab1.R
import co.edu.udea.compumovil.gr08_20201.lab1.ViewModel.POIViewModel
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.dialog_editplace.*
import kotlinx.android.synthetic.main.dialog_editplace.view.*
import kotlinx.android.synthetic.main.fragment_add_place.view.*
import kotlinx.android.synthetic.main.fragment_recycler_view.view.*

class RecyclerViewFragment : Fragment(),PlaceAdapter.onClickListener {

    lateinit var poiViewModel: POIViewModel
    lateinit var adapter: PlaceAdapter
    private var placeList = emptyList<POI>()
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
        poiViewModel = ViewModelProvider(this).get(POIViewModel::class.java)
        poiViewModel.count.observe(viewLifecycleOwner, Observer { cont->
            if(cont==0){
                var point= POI(0,"Playa de Lanikai" ,"Playa arenosa bordeada de palmeras bañada por las aguas azules tropicales del Pacífico, ideales para hacer kayak y de más deportes acuáticos.","La Playa Lanikai es una playa ubicada en Kailua, en la costa este de Oahu, Hawái. Esta playa, con sólo 800 metros de largo, es considerada una de las mejores playas del mundo. Contiguo a esta playa se encuentra un barrio de clase alta, y debido a esto es accsesible sólo a través de caminos públicos. No hay estacionamientos, baños, duchas ni guardavidas.Durante los días de semana la playa es muy solitaria, pero en los fines de semana y durante la temporada turística alta es visitada por multitudes.", "Isla Oahu, Hawái", "25°C",4.0,"R.drawable.lanikai")
                poiViewModel.addPoint(point)
                point= POI(0,"USS Arizona Memorial","Es un monumento a los que perecieron con el hundimiento del USS Arizona, se encuentra sobre los restos de la nave hundida. Algunas porciones se pueden ver sobresaliendo del agua","El USS Arizona fue un acorazado de la clase Pennsylvania de la Armada de Estados Unidos. Este buque fue el tercero en ser nombrado así, en honor al estado número 48 de ese país. Fue construido en 1916 y vio acción en la Primera Guerra Mundial. Es más conocido por su espectacular y catastrófico hundimiento con la pérdida de 1117 vidas, durante el ataque japonés a Pearl Harbor, el 7 de diciembre de 1941","Pearl Harbor, Honolulu-Hawaii","24°C",4.5,"R.drawable.Arizona_Memorial")
                poiViewModel.addPoint(point)
                point= POI(0,"Waikiki","Waikiki, con su bello  tramo de playa frente al mar, es la atracción turística más grande de Hawai, es de fácil acceso y ofrece todas las comodidades y el entretenimiento de una ciudad moderna.","Es un barrio de Honolulu. Las famosas playas creadas por la mano del hombre y sus hoteles son mundialmente reconocidos.A lo largo de sus dos avenidas principales Kalākaua y Kūhiō se disponen la mayoría de los edificios públicos, hoteles y comercios. Mientras que el sector que bordea el canal Alawai es mayormente residencial. Waikiki es prácticamente una península limitada por una parte por el océano y sus playas y la otra el canal descrito.","Isla Oʻahu,Hawái","28°C",5.0,"R.drawable.waikiki")
                poiViewModel.addPoint(point)
                point= POI(0,"Valle de Waipio","Uno de los lugares más concurridos de la Isla Grande de Hawai es el mirador sobre el valle de Waipio, rodeado de frondosos muros de acantilados, se abre al océano, donde la playa de arena negra se encuentra con olas blancas y agua azul."," El Valle de Waipio tiene una milla de ancho en la costa y casi seis millas de profundidad, y a lo largo de la costa hay una hermosa playa de arena negra que a menudo usan las compañías productoras de películas. A ambos lados del valle, hay acantilados que alcanzan casi 2.000 pies con cientos de cascadas, incluida una de las cascadas más famosas de Hawái, Hi’ilawe.","Distrito de Hamakua de la Isla Grande de Hawái","25°C",4.8,"R.drawable.valle")
                poiViewModel.addPoint(point)
                point= POI(0,"Parque Nacional Haleakala","Ofrece acceso al inactivo Haleakala Volcán que se encuentra a más de 10.000 pies. Las vistas desde la cumbre se extienden por toda la isla y son particularmente hermosas al amanecer.","El parque nacional cuenta con más de 30.000 hectáreas con distintos tipos de paisaje, ya que por un lado se pueden visitar las cimas más altas de los montes de Maui, pero por el otro se pueden visitar desiertos o zonas selváticas con cascadas y una gran cantidad de flora. "," Isla Maui,Hawái","20°C-29°C",4.4,"R.drawable.haleakala")
                poiViewModel.addPoint(point)

            }
        })

        poiViewModel.readAllPoints.observe(viewLifecycleOwner, Observer { point ->

            adapter.setData(point)

        })

        view.addButton.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            val fragment = AddPlaceFragment()
            transaction?.replace(R.id.mainFragment, fragment)
            transaction?.commit()
        }

        return view
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
        view.puntuationEditPlaces.setText(item.puntuation.toString())
        view.locationEditPlaces.setText(item.location)
        view.generalInfoEditPlaces.setText(item.generalInfo)

        builder.setPositiveButton("Guardar",
            DialogInterface.OnClickListener { dialog, id ->

                var descriptionEdit= view.descriptionEditInputPlaces.editText?.text.toString()
                var temperatureEdit=view.temperatureEditInputPlaces.editText?.text.toString()
                var puntationEdit=(view.puntuationEditInputPlaces.editText?.text.toString()).toDouble()
                var locationEdit= view.locationEditInputPlaces.editText?.text.toString()
                var generalInfoEdit= view.generalInfoEditInputPlaces.editText?.text.toString()

                Log.d("descrip",descriptionEdit)
                Log.d("temp",temperatureEdit)
                val editPlace=POI(item.id,item.placeName,descriptionEdit,generalInfoEdit, locationEdit, temperatureEdit,puntationEdit,item.image)
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



}




