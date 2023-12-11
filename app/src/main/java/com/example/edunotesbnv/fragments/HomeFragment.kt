package com.example.edunotesbnv.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edunotesbnv.R
import com.example.edunotesbnv.listAdapters.RecyclerViewMainAdapter
import com.example.edunotesbnv.pojo.Topic
import java.util.Locale

class HomeFragment : Fragment() {

    lateinit var topicArrayList: ArrayList<Topic>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // app bar
        val activity = activity
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.title = "Home"
        }
        // recycleview
        val recyclerHomeView= view.findViewById<RecyclerView>(R.id.rvHome)

        val topicArray = arrayOf("Linux", "Python", "PHP", "Laravel", "Docker","DevOps","Javascript")
        val imageidArray = arrayOf(
            R.drawable.linux,
            R.drawable.python,
            R.drawable.php,
            R.drawable.larvel,
            R.drawable.docker,
            R.drawable.devops,
            R.drawable.javascript
        )

        topicArrayList = ArrayList()

        for (i in topicArray.indices) {
            val topic = Topic(topicArray[i], imageidArray[i])
            topicArrayList.add(topic)
        }

        val layoutManager = LinearLayoutManager(requireContext())
        recyclerHomeView.layoutManager = layoutManager

        var adapter = RecyclerViewMainAdapter(topicArrayList,this)
        recyclerHomeView.adapter = adapter
        adapter.setItemClickListener(object  : RecyclerViewMainAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val topicName = topicArray[position].lowercase(Locale.getDefault())


                val quizFragment = QuizFragment()


                val bundle = Bundle()
                bundle.putString("topic_name", topicName)
                quizFragment.arguments = bundle


                parentFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, quizFragment)
                    .addToBackStack(null)
                    .commit()
            }

        })

        return view
    }

}
