package com.example.edunotesbnv.listAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.edunotesbnv.R
import com.example.edunotesbnv.roomDB.Result

class RecyclerViewAdapter(var listOfResult : List<Result>, var context : Fragment):
RecyclerView.Adapter<RecyclerViewAdapter.ResultViewHolder>(){
    class ResultViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView) {
        val timeStamp = itemView.findViewById<TextView>(R.id.tvTopicR)
        val topic = itemView.findViewById<TextView>(R.id.tvTimeStamp)
        val marks = itemView.findViewById<TextView>(R.id.tvMarks)
        //val outOff = itemView.findViewById<TextView>(R.id.tvOutOff)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemV = LayoutInflater.from(parent.context).inflate(R.layout.result_items,parent,false)
        return ResultViewHolder(itemV)
    }

    override fun getItemCount(): Int {
        return listOfResult.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        var currentItem=listOfResult[position]
        holder.topic.text = currentItem.topic
        holder.timeStamp.text =currentItem.timeStamp
//        holder.marks.text =currentItem.resultScore.toString()
        //holder.outOff.text = currentItem.quizSize.toString()

        holder.marks.text = "Your Score was ${currentItem.resultScore} / ${currentItem.quizSize}"
    }

}