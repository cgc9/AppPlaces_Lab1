package co.edu.udea.compumovil.gr08_20201.lab2.Fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.Comment
import co.edu.udea.compumovil.gr08_20201.lab2.Entities.POI
import co.edu.udea.compumovil.gr08_20201.lab2.R
import kotlinx.android.synthetic.main.card_view.view.*
import kotlinx.android.synthetic.main.comment_view.view.*

class CommentAdapter(val commentList:List<Comment>): RecyclerView.Adapter<CommentAdapter.MyViewHolder>()  {

    //private var commentList = emptyList<Comment>()


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val textViewComment: TextView =itemView.text_comment
        val textViewPuntuacion: TextView =itemView.puntuation_comment
        val textViewValor: TextView =itemView.valor_comment
      /*  fun initialize(item: POI, action: onClickListener) {
            itemView.setOnClickListener {
                action.onItemClick(item, adapterPosition)
            }

            itemView.delete.setOnClickListener {
                action.onItemDelete(item, adapterPosition)
            }

            itemView.edit.setOnClickListener {
                action.onItemEdit(item, adapterPosition)
            }


        }*/
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = commentList[position]
        holder.textViewComment.text=currentItem.comment
        holder.textViewValor.text=currentItem.userPuntuation.toString()


    }

    /*
    interface onClickListener {
        fun onItemClick(item: POI, index: Int)
        fun onItemDelete(item: POI, index: Int)
        fun onItemEdit(item: POI, index: Int)
    }

    fun setData(comments: List<Comment>) {
        this.commentList = comments
        notifyDataSetChanged()
    }*/
}