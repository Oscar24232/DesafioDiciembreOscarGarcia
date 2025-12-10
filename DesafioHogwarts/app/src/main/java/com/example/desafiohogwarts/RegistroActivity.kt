package com.example.desafiohogwarts

import GestionPreferenciasYRegistrosViewModel
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.Api.UsuarioCompartido
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

        // ⬇️ ESTE OBSERVER DEBE IR AQUÍ, FUERA DEL BOTÓN
        viewModel.usuarioCompleto.observe(this) { usuario ->
            if (usuario != null) {

                UsuarioCompartido.usuario = usuario  // Guardamos usuario global

                when (usuario.rol) {
                    4 -> startActivity(Intent(this, Activity_alumno::class.java))
                    3 -> startActivity(Intent(this, ActivityProfesor::class.java))
                }
            }
        }

        binding.btnAtras.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }

        binding.btSombrero.setOnClickListener {

            val casasPreferencias = hashMapOf(
                "Gryffindor" to binding.seekGryffindor.progress,
                "Hufflepuff" to binding.seekHufflepuff.progress,
                "Ravenclaw" to binding.seekRavenclaw.progress,
                "Slytherin" to binding.seekSlytherin.progress
            )

            val req = PreferenciasCasa(casasPreferencias)
            viewModel.enviarPreferencias(req)

            var nivel = 0
            var rol = 0

            if (binding.rbAlumno.isChecked) {
                nivel = 1
                rol = 4
            } else if (binding.rbProfesor.isChecked) {
                nivel = 2
                rol = 3
            } else {
                nivel = 5
                rol = 2
            }

            var casaElegida = 0

            viewModel.idCasaAsignada.observe(this) { id ->
                casaElegida = id
            }

            user = Usuario(
                nombre = binding.etNombre.text.toString(),
                email = binding.etEmail.text.toString(),
                contrasena = binding.etPass.text.toString(),
                foto = "sinFoto.png",
                id_casa = casaElegida,
                exp = 0,
                nivel = nivel,
                estado = "1",
                rol = rol
            )

            viewModel.insertarUsuario(user)

            viewModel.exito.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

            viewModel.error.observe(this) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }

            // ⬇️ Esto activará el observer que ya está arriba
            viewModel.cargarUsuarioPorEmail(user.email)
        }
    }
}