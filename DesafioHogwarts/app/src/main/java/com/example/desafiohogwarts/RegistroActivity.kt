package com.example.desafiohogwarts

import GestionPreferenciasYRegistrosViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.databinding.ActivityRegistroBinding
import com.example.desafiohogwarts.modelo.PreferenciasCasa
import modelo.Usuario


class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private val viewModel: GestionPreferenciasYRegistrosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // CASA ASIGNADA POR EL SOMBRERO
        viewModel.idCasaAsignada.observe(this) { idCasa ->
            if (idCasa != null) {
                registrarUsuario(idCasa)
            }
        }

        viewModel.exito.observe(this) {
            // después de insertar, cargamos el usuario
            viewModel.cargarUsuarioPorEmail(
                binding.etEmail.text.toString()
            )
        }

        // ERRORES
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }

        // USUARIO COMPLETO → ENTRAR
        viewModel.usuarioCompleto.observe(this) { usuario ->
            if (usuario != null) {
                DatosCompartidos.usuario = usuario

                when (usuario.rol) {
                    4 -> startActivity(Intent(this, Activity_alumno::class.java))
                    3 -> startActivity(Intent(this, ActivityProfesor::class.java))
                }
            }
        }


        // BOTÓN


        binding.btSombrero.setOnClickListener {
            val casasPreferencias = hashMapOf(
                "Gryffindor" to binding.seekGryffindor.progress,
                "Hufflepuff" to binding.seekHufflepuff.progress,
                "Ravenclaw" to binding.seekRavenclaw.progress,
                "Slytherin" to binding.seekSlytherin.progress
            )

            viewModel.enviarPreferencias(PreferenciasCasa(casasPreferencias))
        }
    }


    //FUNCIÓN QUE REGISTRA EL USUARIO
    private fun registrarUsuario(idCasa: Int) {

        val rol = when {
            binding.rbAlumno.isChecked -> 4
            binding.rbProfesor.isChecked -> 3
            else -> 2
        }

        val nivel = if (rol == 4) 1 else 2

        val usuario = Usuario(
            nombre = binding.etNombre.text.toString(),
            email = binding.etEmail.text.toString(),
            contrasena = binding.etPass.text.toString(),
            foto = "sinFoto.png",
            id_casa = idCasa,
            exp = 0,
            nivel = nivel,
            estado = "1",
            rol = rol
        )

        viewModel.insertarUsuario(usuario)
    }
}
