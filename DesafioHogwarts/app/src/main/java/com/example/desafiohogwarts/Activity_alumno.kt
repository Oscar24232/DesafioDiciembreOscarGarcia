package com.example.desafiohogwarts


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.ViewModel.Viewmodel_activity_alumno
import com.example.desafiohogwarts.databinding.ActivityAlumnoBinding
import modelo.Usuario

class Activity_alumno : AppCompatActivity() {

    private lateinit var binding: ActivityAlumnoBinding
    private val vmClases: Viewmodel_activity_alumno by viewModels()

    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // USUARIO DESDE EL OBJECT COMPARTIDO
        usuario = DatosCompartidos.usuario

        Log.d("AlumnoActivity", "Usuario cargado -> $usuario")

        // CARGAR ASIGNATURAS
        vmClases.cargarAsignaturas(usuario.id)

        // MOSTRAR DATOS EN PANTALLA
        binding.txtNombreAlumno.text = usuario.nombre
        binding.txtEmailAlumno.text = usuario.email
        binding.txtNivelAlumno.text = "Nivel: ${usuario.nivel}"
        binding.txtExpAlumno.text = "Experiencia: ${usuario.exp}"

        binding.txtEstadoAlumno.text =
            if (usuario.estado == "1") "Estado: Activo" else "Estado: Inactivo"

        binding.txtCasaAlumno.text = "Casa: ${nombreCasa(usuario.id_casa)}"

        // OBSERVAR ASIGNATURAS PARA ACTIVAR BOTONES
        vmClases.asignaturasAlumno.observe(this) { clases ->
            binding.btnClasePociones.isEnabled = clases.contains(1)
            binding.btnClaseHechizos.isEnabled = clases.contains(2)
        }

        // VER MI CASA
        binding.btnVerCasa.setOnClickListener {
            val intent = Intent(this, Activity_miCasa::class.java)

            startActivity(intent)
        }

        // LOGOUT
        binding.btnLogoutAlumno.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }

        binding.btnClaseHechizos.setOnClickListener {
            startActivity(Intent(this, ActivityHechizosAlumno::class.java))
        }
        binding.btHechizoAprendido.setOnClickListener {
            Log.d("clic", "Usuario: ${DatosCompartidos.usuario}")

            startActivity(Intent(this, Activity_hechizos_alumnos_aprendidos::class.java))
        }
        binding.btRankingCasa.setOnClickListener{
            Log.d("clic", "Usuario: ${DatosCompartidos.usuario}")
            startActivity(Intent(this, ActivityRankingCasas::class.java))
        }
    }


    private fun nombreCasa(id: Int): String {
        return when (id) {
            1 -> "Gryffindor"
            2 -> "Slytherin"
            3 -> "Hufflepuff"
            4 -> "Ravenclaw"
            else -> "Sin casa"
        }
    }
}