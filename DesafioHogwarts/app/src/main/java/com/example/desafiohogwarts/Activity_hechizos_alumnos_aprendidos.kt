package com.example.desafiohogwarts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Alumnos.AdapterHechizosAprendidos
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.ViewModel.ViewModelHechizosAprendidos
import com.example.desafiohogwarts.databinding.ActivityHechizosAlumnoBinding

class Activity_hechizos_alumnos_aprendidos : AppCompatActivity() {
    private lateinit var binding: ActivityHechizosAlumnoBinding
    private val vm: ViewModelHechizosAprendidos by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHechizosAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usuario = DatosCompartidos.usuario ?: return

        binding.rvHechizos.layoutManager = LinearLayoutManager(this)

        vm.cargarHechizosAprendidos(usuario.id!!)

        vm.listaAprendidos.observe(this) { lista ->
            binding.rvHechizos.adapter =
                AdapterHechizosAprendidos(lista.toMutableList())
        }

        // El botón que ya existe en el layout compartido
        binding.btnVolverHechizos.setOnClickListener { finish() }
    }
}
