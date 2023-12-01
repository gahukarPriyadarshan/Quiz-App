package com.example.edunotesbnv.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.edunotesbnv.R
import com.example.edunotesbnv.databinding.FragmentQuizResultBinding
import com.example.edunotesbnv.roomDB.Result
import com.example.edunotesbnv.roomDB.RoomDataB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class QuizResultFragment : Fragment() {

    private lateinit var binding : FragmentQuizResultBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizResultBinding.inflate(inflater,container,false)

        val arguments = requireArguments()
        val topicName = arguments.getString("topic_name_to_display").toString()
        val lengthQuiz = arguments.getInt("length_quiz")
        val resultQuiz = arguments.getInt("result_count")

        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.title = "Result"
        }

        binding.apply {
            tvTopic.text = topicName
            tvLength.text = lengthQuiz.toString()
            if(resultQuiz == 10){
                tvResult.text = resultQuiz.toString()
            }
            else{
                tvResult.text = "0${resultQuiz}"
            }

        }

        val context : Context =requireContext()
        binding.btnSaveResult.setOnClickListener{
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
            val current = LocalDateTime.now().format(formatter).toString()
            val result = Result(0,topicName.uppercase(),current,resultQuiz,lengthQuiz)
            val database = RoomDataB.getDatabase(context)

            lifecycleScope.launch(Dispatchers.IO) {
                database.dao().insert(result)
            }

            val resultFragment = ResultFragment()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout,resultFragment )
            transaction.commit()

        }

        return binding.root
    }

}