package com.example.desafiohogwarts

import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.Api.DatosCompartidos
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Profesor.AdapterAsignaturasProfesor
import com.example.desafiohogwarts.ViewModel.ProfesorViewModel
import com.example.desafiohogwarts.databinding.ActivityProfesorBinding
import com.example.desafiohogwarts.modelo.Asignaturas


class ActivityProfesor : AppCompatActivity() {

    private lateinit var binding: ActivityProfesorBinding
    private lateinit var viewModel: ProfesorViewModel
    private lateinit var adapter: AdapterAsignaturasProfesor

    private val idProfesor = DatosCompartidos.usuario.id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfesorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ProfesorViewModel::class.java]

        // CONFIGURAR ADAPTER Y RECYCLER
        adapter = AdapterAsignaturasProfesor(mutableListOf(), this)
        binding.rvAsignaturas.layoutManager = LinearLayoutManager(this)
        binding.rvAsignaturas.adapter = adapter


        // OBSERVADORES DEL VIEWMODEL
        viewModel.asignaturas.observe(this) { lista ->
            adapter.actualizarLista(lista)
        }

        viewModel.mensaje.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        binding.button.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }


        // CARGAR ASIGNATURAS DEL PROFESOR
        viewModel.cargarAsignaturas(idProfesor)
    }

    // FUNCIÓN QUE LLAMA EL ADAPTER -> IR A DETALLE DE LA ASIGNATURA
    fun irDetalleAsignatura(asignatura: Asignaturas) {

        // Guardar la asignatura en el objeto compartido
        DatosCompartidos.asignatura = asignatura

        // Navegar a la actividad detalle
        val i = Intent(this, AsignaturaDetailActivity::class.java)
        startActivity(i)
    }

}
