package com.nafis.skilltestprodia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nafis.skilltestprodia.databinding.ActivityMainBinding
import com.nafis.skilltestprodia.ui.HomeFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val fragmentManager = supportFragmentManager
        val signInFragment = HomeFragment()
        fragmentManager
            .beginTransaction()
            .add(R.id.main_frame_container, signInFragment, HomeFragment::class.java.simpleName)
            .commit()
    }

}