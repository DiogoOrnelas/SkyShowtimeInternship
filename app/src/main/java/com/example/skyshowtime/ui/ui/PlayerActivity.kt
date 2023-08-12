package com.example.skyshowtime.ui.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.ActivityPlayerBinding
import com.example.skyshowtime.ui.adapter.MyPlayerAdapter

class PlayerActivity: AppCompatActivity(){

    private lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val layoutManager = LinearLayoutManager( this, LinearLayoutManager.HORIZONTAL, false )

        val args : PDPFragmentArgs by navArgs()

        val selectedItem = args.item
        val clAdapter = MyPlayerAdapter(selectedItem) { item ->
            Log.i("Teste: ", item.title)
        }

        binding.rcPlayer.adapter = clAdapter
        binding.rcPlayer.layoutManager = layoutManager
    }

    override fun onDetachedFromWindow() {
        window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onDetachedFromWindow()
    }
}