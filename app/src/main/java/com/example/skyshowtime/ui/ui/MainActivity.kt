package com.example.skyshowtime.ui.ui


import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.skyshowtime.R
import com.example.skyshowtime.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val actionController by lazy { findNavController(this, R.id.activity_main_nav_host_fragment) }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)

        this.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        val navController = findNavController( this, R.id.activity_main_nav_host_fragment)

        setupWithNavController(binding.bottomNav, navController)

        val appBarConfiguration = AppBarConfiguration( setOf(R.id.homeFragment, R.id.searchFragment) )
        setupActionBarWithNavController(actionController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return actionController.navigateUp() || super.onSupportNavigateUp()
    }
}