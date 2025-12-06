package com.example.desafiohogwarts

import GestionPreferenciasYRegistrosViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.databinding.ActivityRegistroBinding
import com.example.desafiohogwarts.modelo.PreferenciasCasa
import modelo.Usuario


class RegistroActivity: AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: GestionPreferenciasYRegistrosViewModel by viewModels()
    lateinit var user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAtras.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btSombrero.setOnClickListener {
            var nombre = binding.etNombre
            var email = binding.etEmail
            var pass = binding.etPass
            val casasPreferencias = hashMapOf(
                "Gryffindor" to binding.seekGryffindor.progress,
                "Hufflepuff" to binding.seekHufflepuff.progress,
                "Ravenclaw" to binding.seekRavenclaw.progress,
                "Slytherin" to binding.seekSlytherin.progress
            )
             var casaElegida=0
            //log
            Log.d("CasasPreferencias", "Gryffindor: ${casasPreferencias["Gryffindor"]}")
            Log.d("CasasPreferencias", "Hufflepuff: ${casasPreferencias["Hufflepuff"]}")
            Log.d("CasasPreferencias", "Ravenclaw: ${casasPreferencias["Ravenclaw"]}")
            Log.d("CasasPreferencias", "Slytherin: ${casasPreferencias["Slytherin"]}")
            val req = PreferenciasCasa(casasPreferencias)
            //enviar preferencias al ktor
            viewModel.enviarPreferencias(req)
            var nivel=0
            var rol=0
            if (binding.rbAlumno.isChecked){
                nivel=1
                rol=4
            }
            else if (binding.rbProfesor.isChecked){
                nivel=2
                rol=3
            }
            else{
                nivel=5
                rol=2
            }

            viewModel.idCasaAsignada.observe(this) { id ->
                casaElegida = id //esto es el id de la casa que tiene guardada el observe

            }
           //quiero poner un mensaje para el usuario con la casa elegida
           //hacer el insert en la bbdd
            user = Usuario(
                nombre = binding.etNombre.text.toString(),
                email = binding.etEmail.text.toString(),
                contrasena = binding.etPass.text.toString(),
                foto = "sinFoto.png",
                id_casa = casaElegida,
                exp = 0,      // siempre nace en 0
                nivel = nivel,    // alumno empieza en nivel 1 (salvo profesor/admin)
                estado = "1",
                rol=rol
            )
            viewModel.insertarUsuario(user)
            viewModel.exito.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

            viewModel.error.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
            // Llamamos para obtener el usuario COMPLETO con su rol
            viewModel.cargarUsuarioPorEmail(this@RegistroActivity.user.email)

            viewModel.usuarioCompleto.observe(this) { usuario ->
                if (usuario != null) {

                    when (usuario.rol) {
                        4 -> {
                            startActivity(
                                Intent(this, ActivityAlumno::class.java)
                                    .putExtra("usuario", usuario)
                            )
                        }

                        3 -> {
                            startActivity(
                                Intent(this, ActivityProfesor::class.java)
                                    .putExtra("usuario", usuario)
                            )
                        }


                    }
                }
            }

        }
    }

}