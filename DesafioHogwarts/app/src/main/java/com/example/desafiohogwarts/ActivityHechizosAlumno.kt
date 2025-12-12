package com.example.desafiohogwarts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Alumnos.Adapter_alumno_hechizos
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.ViewModel.Viewmodel_hechizos_alumno
import com.example.desafiohogwarts.databinding.ActivityHechizosAlumnoBinding
import modelo.Usuario

class ActivityHechizosAlumno : AppCompatActivity() {
    private lateinit var binding: ActivityHechizosAlumnoBinding
    private val vm: Viewmodel_hechizos_alumno by viewModels()
    private lateinit var usuario: Usuario
    private lateinit var adapter: Adapter_alumno_hechizos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHechizosAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuario = DatosCompartidos.usuario

        // LAYOU
        binding.rvHechizos.layoutManager = LinearLayoutManager(this)

        //Inicializamos adapter vacío (IMPORTANTE)
        adapter = Adapter_alumno_hechizos(
            mutableListOf(),
            emptyList(),
            this
        )
        binding.rvHechizos.adapter = adapter


        //Cargar hechizos y aprendidos al inicio
        vm.cargarHechizos()
        vm.cargarHechizosAprendidos(usuario.id!!)

        //Actualizar lista de hechizos
        vm.hechizos.observe(this) { listaHechizos ->
            adapter.actualizarHechizos(listaHechizos)
        }

        // Actualizar lista aprendidos
        vm.aprendidos.observe(this) { listaAprendidos ->
            adapter.actualizarAprendidos(listaAprendidos)
        }

        vm.puedeEntrar.observe(this) { ok ->
            if (!ok) {
                Toast.makeText(this, "No tienes acceso a esta asignatura", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        vm.mensaje.observe(this) { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        binding.btnVolverHechizos.setOnClickListener { finish() }
    }

    fun aprenderHechizo(idHechizo: Int) {
        vm.aprenderHechizo(usuario.id!!, idHechizo)
    }
}
