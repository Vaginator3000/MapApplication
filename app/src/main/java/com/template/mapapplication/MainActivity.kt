package com.template.mapapplication

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.template.mapapplication.databinding.ActivityMainBinding
import com.yandex.mapkit.MapKitFactory

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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