package com.example.desafiohogwarts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Adapter_alumno_hechizos
import com.example.desafiohogwarts.Api.UsuarioCompartido
import com.example.desafiohogwarts.ViewModel.Viewmodel_hechizos_alumno
import com.example.desafiohogwarts.databinding.ActivityHechizosAlumnoBinding
import modelo.Usuario

class ActivityHechizosAlumno : AppCompatActivity() {
    private lateinit var binding: ActivityHechizosAlumnoBinding
    private val viewModels: Viewmodel_hechizos_alumno by viewModels()
    private lateinit var usuario: Usuario   // → viene del intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHechizosAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 1) RECIBIR USUARIO
        usuario = UsuarioCompartido.usuario


        binding.rvHechizos.layoutManager = LinearLayoutManager(this)
        //hechizos aprendidos
        viewModels.cargarHechizosAprendidos(usuario.id!!)
        // 2) COMPROBAR SI EL ALUMNO TIENE LA ASIGNATURA
        viewModels.comprobarAsignatura(idAlumno = usuario.id)

        // 3) OBSERVAR SI PUEDE ENTRAR
        viewModels.puedeEntrar.observe(this) { ok ->
            if (ok) {
                // Si tiene la asignatura, cargamos los hechizos
                viewModels.cargarHechizos()
            } else {
                Toast.makeText(this, "No tienes acceso a esta asignatura", Toast.LENGTH_LONG).show()
            }
        }

        // 4) OBSERVAR HECHIZOS Y CARGAR EL ADAPTER
        viewModels.hechizos.observe(this) { lista ->
            binding.rvHechizos.adapter =
                Adapter_alumno_hechizos(lista.toMutableList(), this)
        }

        // 5) MENSAJES DEL VIEWMODEL (APRENDER HECHIZO)
        viewModels.mensaje.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // 6) BOTÓN VOLVER (si lo tienes)
        binding.btnVolverHechizos.setOnClickListener {
            finish()
        }
    }

    // EL ADAPTER LLAMA A ESTA FUNCIÓN
    fun aprenderHechizo(idHechizo: Int) {
        viewModels.aprenderHechizo(

            idAlumno = usuario.id,
            idHechizo = idHechizo
        )
    }
}
