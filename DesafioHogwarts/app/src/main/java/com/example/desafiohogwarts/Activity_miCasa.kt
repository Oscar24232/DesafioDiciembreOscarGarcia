package com.example.desafiohogwarts


import Api.UserNetwork
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.desafiohogwarts.databinding.ActivityMiCasaBinding
import kotlinx.coroutines.launch
import modelo.Usuario

class Activity_miCasa : AppCompatActivity() {

    private lateinit var binding: ActivityMiCasaBinding
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMiCasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir usuario
        val idCasa = intent.getIntExtra("idCasa", -1)

        cargarCasa(idCasa)
        binding.btAtras.setOnClickListener{
            finish()
        }
    }

    private fun cargarCasa(idCasa: Int) {
        lifecycleScope.launch {
            try {
                val resp = UserNetwork.retrofit.obtenerCasa (idCasa)

                if (resp.isSuccessful && resp.body() != null) {
                    val casa = resp.body()!!

                    // Mostrar datos
                    binding.tvNombreCasa.text = casa.nombre
                    binding.tvPuntosCasa.text = "Puntos: ${casa.puntuacion}"
                    binding.tvAlumnosCasa.text = "Miembros: ${casa.num_alumnos}"

                    // Imagen según la casa
                    val escudo = when (casa.id_casa) {
                        1 -> R.drawable.grifin2
                        2 -> R.drawable.huffle
                        3 -> R.drawable.raven
                        4 -> R.drawable.sly
                        else -> R.drawable.fondohogwart
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
