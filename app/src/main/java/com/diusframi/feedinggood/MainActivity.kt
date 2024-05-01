package com.diusframi.feedinggood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.diusframi.feedinggood.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_activity_main)

        /** Con la siguiente línea ("setupActionBarWithNavController(this, navController)") hacemos
         * que el Toolbar (Barra superior de pantallas Android sea gestionado por nuestro
         * navController (Navegación por grafos de Jetpack): */
        setupActionBarWithNavController(this, navController)
    }

    /** Para hacer que la flechita de la top bar vuelva hacia el fragment que lo contiene */
    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment_activity_main).navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.userList -> {
                makeToast("List")

                return true
            }

            R.id.logout -> {
                makeToast("logout")

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}