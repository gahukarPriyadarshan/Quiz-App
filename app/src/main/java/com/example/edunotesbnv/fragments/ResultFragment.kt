package com.example.edunotesbnv.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.edunotesbnv.databinding.FragmentResultBinding
import com.example.edunotesbnv.listAdapters.RecyclerViewResultAdapter
import com.example.edunotesbnv.roomDB.Result
import com.example.edunotesbnv.roomDB.RoomDataB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ResultFragment : Fragment(){
    lateinit var binding : FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var listOfResult :List<Result>
        binding = FragmentResultBinding.inflate(inflater,container,false)

        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.title = "Results"
        }

        val resultRecyclerView = binding.recyclerView
        resultRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val context : Context =requireContext()
        val database = RoomDataB.getDatabase(context)

        lifecycleScope.launch(Dispatchers.IO) {
            listOfResult = database.dao().getAllResult()

            withContext(Dispatchers.Main) {
                resultRecyclerView.adapter = RecyclerViewResultAdapter(listOfResult, this@ResultFragment)
            }
        }

        return binding.root
    }
}