package com.example.desafiohogwarts


import Api.UserNetwork
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.ViewModel.ViewModelCasa
import com.example.desafiohogwarts.databinding.ActivityMiCasaBinding
import kotlinx.coroutines.launch
import modelo.Usuario

class Activity_miCasa : AppCompatActivity() {

    private lateinit var binding: ActivityMiCasaBinding
    private lateinit var usuario: Usuario
    private lateinit var viewModelCasa: ViewModelCasa

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiCasaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        usuario = DatosCompartidos.usuario?: return   // por si acaso fuera null (seguridad extra)

        // tener casa
        val idCasa = usuario.id_casa

        cargarCasa(idCasa)

        binding.btAtras.setOnClickListener {
            finish()
        }
    }

    private fun cargarCasa(idCasa: Int) {
        lifecycleScope.launch {
            try {
                val resp = UserNetwork.retrofit.obtenerCasa(idCasa)

                if (resp.isSuccessful && resp.body() != null) {
                    val casa = resp.body()!!
                    Log.d("DEBUG_CASA1","${casa.id_casa}, ${casa.nombre}")
                    // Mostrar datos
                    binding.tvNombreCasa.text = casa.nombre
                    binding.tvPuntosCasa.text = "Puntos: ${casa.puntuacion}"
                    binding.tvAlumnosCasa.text = "Miembros: ${casa.num_alumnos}"
                    Log.d("DEBUG_CASA2","${casa.id_casa}, ${casa.nombre}")
                    // Imagen según la casa
                    val escudo = when (casa.id_casa) {
                        1 -> R.drawable.grifin2
                        2 -> R.drawable.sly
                        3 -> R.drawable.huffle
                        else -> R.drawable.raven
                    }
                    binding.ivEscudoCasa.setImageResource(escudo)

                } else {
                    Toast.makeText(this@Activity_miCasa, "Error al cargar la casa", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(this@Activity_miCasa, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
