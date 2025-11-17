package com.example.desafiohogwarts


import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.desafiohogwarts.databinding.ActivitySuperAdminBinding

class ActivityDios : AppCompatActivity() {
    private lateinit var binding: ActivitySuperAdminBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuperAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configura el Toolbar
        setSupportActionBar(binding.toolbar)

        //Inicializa DrawerLayout
        val drawerLayout: DrawerLayout = binding.drawerLayout

        //Habilita el botón de hamburguesa para abrir el drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
        val superdios= GestionAsignaturasFragment
    }

    //Método para abrir el drawer al presionar el ícono de menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                val drawerLayout: DrawerLayout = binding.drawerLayout
                drawerLayout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}