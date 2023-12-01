package com.example.edunotesbnv

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.edunotesbnv.databinding.ActivityMainBinding
import com.example.edunotesbnv.fragments.HomeFragment
import com.example.edunotesbnv.fragments.ResultFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar)

        replaceViewByFragment(HomeFragment())

        binding.bottomView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceViewByFragment(HomeFragment())
                R.id.result -> replaceViewByFragment(ResultFragment())
                else -> {

                }
            }
            true
        }
    }

    private fun replaceViewByFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }
}