package com.example.edunotesbnv.listAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.edunotesbnv.R
import com.example.edunotesbnv.pojo.Topic
import com.google.android.material.imageview.ShapeableImageView

class RecyclerViewMainAdapter(private val arrayList: ArrayList<Topic>,var context : Fragment) :
    RecyclerView.Adapter<RecyclerViewMainAdapter.ViewHolder>() {

    // for item click
    private lateinit var myListener: OnItemClickListener

    class ViewHolder(view: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(view) {
        val image: ShapeableImageView = view.findViewById(R.id.imgV)
        val text: TextView = view.findViewById(R.id.tvTopic)

        //for item click
        init{
            view.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }

    // for item click
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // for item click
    fun setItemClickListener(listener: OnItemClickListener){
        myListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_items, parent, false)
        return ViewHolder(view,myListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = arrayList[position].topic
        holder.image.setImageResource(arrayList[position].image)
    }

    override fun getItemCount(): Int {

        return arrayList.size
    }
}
