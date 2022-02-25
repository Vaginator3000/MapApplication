package com.template.mapapplication

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.template.mapapplication.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_map, R.id.navigation_places
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Скрываю таббар на экране авторизации
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navView.isVisible =
                if (destination.id == R.id.navigation_login) {
                    false
                } else {
                    true
                }
        }

        //Чтоб не заливать ключ на гит
        MapKitFactory.setApiKey(KeyClass().key)
    }

    //Избегаю возвращения на фрагмент с авторизацией
    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        if (navController.currentDestination?.id == R.id.navigation_login) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
}