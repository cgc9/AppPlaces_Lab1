package co.edu.udea.compumovil.gr08_20201.lab2.Fragments

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.card_view.view.image
import kotlinx.android.synthetic.main.fragment_detail.view.*

class PlaceAdapter(val clickListener: onClickListener) :
    RecyclerView.Adapter<PlaceAdapter.MyViewHolder>() {

    private var placeList = emptyList<POI>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun initialize(item: POI, action: onClickListener) {
            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }

            itemView.delete.setOnClickListener {
                action.onItemDelete(item, adapterPosition)
            }

            itemView.edit.setOnClickListener {
                action.onItemEdit(item, adapterPosition)
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = placeList[position]
        var url:String
/*
        if(placeList[position].id <= 5){
            url=currentItem.image
        }else{
            url="https://media-cdn.tripadvisor.com/media/photo-s/1a/dd/6a/f0/paradise-taveuni.jpg"
        }*/

        holder.itemView.namePlace.text = currentItem.placeName
        holder.itemView.placeDescription.text = currentItem.description
        holder.itemView.valor.text = currentItem.generalPuntuation.toString()
        Picasso.get()
            .load(currentItem.image)
            .into(holder.itemView.image)
        holder.initialize(placeList.get(position), clickListener)


    }

    interface onClickListener {
        fun onItemClick(item: POI, index: Int)
        fun onItemDelete(item: POI, index: Int)
        fun onItemEdit(item: POI,index: Int)
    }

    fun setData(places: List<POI>) {
        this.placeList = places
        notifyDataSetChanged()
    }

}