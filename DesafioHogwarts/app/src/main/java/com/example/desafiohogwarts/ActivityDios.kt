package com.example.desafiohogwarts

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafiohogwarts.databinding.ActivitySuperAdminBinding
import com.google.android.material.navigation.NavigationView

class ActivityDios : AppCompatActivity() {
    private lateinit var binding: ActivitySuperAdminBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuperAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Configura el Toolbar
        setSupportActionBar(binding.toolbar)

        //Inicializa DrawerLayout
        val drawerLayout: DrawerLayout = binding.drawerLayout

        //Habilita el botón de hamburguesa para abrir el drawer de forma manual, si quiero personalizarlo.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)

        // Para iniciar el navController que me permitirá navegar de un fragmento a otro.
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.navController
        //En el archivo mi_nav.xml los id de los fragmentos en el nav_graph debe coincidir con los id de los elementos en mi_menu.xml.
        //Es decir para que funcione esto de arriba los fragmentos se llamarán:
        // -- en mi_nav: navigation_fragmento_a
        // -- en menu: navigation_fragmento_a


        //Define los destinos principales para que siempre muestre el ícono de hamburguesa en lugar de la flecha de retroceso
        //Configura AppBarConfiguration con tus destinos principales.
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.gestionUsuarios, R.id.gestionAsignaturasFragment, R.id.gestionCasas,R.id.gestionIngredientes,R.id.gestionPociones,R.id.gestionHechizos),
            drawerLayout
        )
        //appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout) //--> De esta manera solo se mostraría el ícono de hamburguesa en el Fragmento 1 (principal) y retroceso en el resto.

        //Configura AppBarConfiguration para sincronizar el botón de hamburguesa.
        //Vincula el NavController con el ActionBar
        setupActionBarWithNavController(navController, appBarConfiguration)

        //Como se carga el primer elemento, ponemos el título del primer fragmento.
        supportActionBar?.title = "Gestio Usuarios"

        //Configura NavigationView
        val navView: NavigationView = binding.navigationView
        navView.setNavigationItemSelectedListener { menuItem ->
            //Maneja las selecciones del menú aquí
            when (menuItem.itemId) {
                R.id.gestionUsuarios -> {
                    navController.navigate(R.id.gestionUsuarios)
                    supportActionBar?.title = "Usuarios"
//                    supportActionBar?.hide()
                    Toast.makeText(this, "Opción 1", Toast.LENGTH_LONG).show()
                }
                R.id.gestionCasas -> {
                    navController.navigate(R.id.gestionCasas)
                    supportActionBar?.title = "Casas"
//                    Toast.makeText(this, "Opción 2", Toast.LENGTH_LONG).show()
                }
                R.id.gestionAsignaturasFragment -> {
                    navController.navigate(R.id.gestionAsignaturasFragment)
                    supportActionBar?.title = "Asignaturas"
//                    Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show()
                }
                R.id.gestionIngredientes -> {
                    navController.navigate(R.id.gestionIngredientes)
                    supportActionBar?.title = "Ingredientes"
//                    Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show()
                }
                R.id.gestionPociones -> {
                    navController.navigate(R.id.gestionPociones)
                    supportActionBar?.title = "Pociones"
//                    Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show()
                }
                R.id.gestionHechizos -> {
                    navController.navigate(R.id.gestionHechizos)
                    supportActionBar?.title = "Hechizos"
//                    Toast.makeText(this, "Opción 3", Toast.LENGTH_LONG).show()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START) //Cierra el drawer después de seleccionar una opción
            true
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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