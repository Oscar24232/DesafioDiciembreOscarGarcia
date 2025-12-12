package com.example.desafiohogwarts

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Profesor.AdapterAlumnosAsignatura
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.ViewModel.AsignaturaDetailViewModel
import com.example.desafiohogwarts.databinding.ActivityAsignaturaDetalleBinding
import modelo.Usuario


class AsignaturaDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAsignaturaDetalleBinding
    private lateinit var viewModel: AsignaturaDetailViewModel
    private lateinit var adapter: AdapterAlumnosAsignatura

    private var idAsignatura: Int? = DatosCompartidos.asignatura.id_asignatura


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsignaturaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[AsignaturaDetailViewModel::class.java]
        binding.tvAsignaturaTitle.text = DatosCompartidos.asignatura.nombre
        Log.d("ASIGNATURA_DEBUG", "Asignatura recibida: id=${DatosCompartidos.asignatura.id_asignatura}, nombre=${DatosCompartidos.asignatura.nombre}")


        binding.tvAsignaturaTitle.text ="Curso "+ DatosCompartidos.asignatura.nombre


        // CONFIGURAR RECYCLER Y ADAPTER
        adapter = AdapterAlumnosAsignatura(mutableListOf(), this)
        binding.rvAlumnosAsignatura.layoutManager = LinearLayoutManager(this)
        binding.rvAlumnosAsignatura.adapter = adapter


        // OBSERVADORES VIEWMODEL
        viewModel.alumnos.observe(this) { lista ->
            adapter.actualizarLista(lista)
        }

        viewModel.todosAlumnos.observe(this) { lista ->
            mostrarDialogoSeleccionAlumno(lista)
        }

        viewModel.mensaje.observe(this) { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }


        binding.btnAddAlumno.setOnClickListener {
            viewModel.cargarTodosLosAlumnos()
        }



        // CARGAR LOS ALUMNOS DE ESTA ASIGNATURA
        viewModel.cargarAlumnosDeAsignatura(idAsignatura as Int?)
        binding.btVolver.setOnClickListener {
            finish()
        }
    }



    private fun mostrarDialogoSeleccionAlumno(lista: List<Usuario>) {

        if (lista.isEmpty()) {
            Toast.makeText(this, "No hay alumnos disponibles", Toast.LENGTH_SHORT).show()
            return
        }

        val nombres = lista.map { usuario ->
            if (!usuario.nombre.isNullOrBlank())
                "${usuario.nombre} "
            else
                usuario.nombre
        }.toTypedArray()

        AlertDialog.Builder(this)
            .setTitle("Seleccionar alumno")
            .setItems(nombres) { _, index ->
                val alumno = lista[index]
                viewModel.añadirAlumno(idAsignatura, alumno.id)
            }
            .show()
    }



    // LLAMADO POR EL ADAPTER PARA ELIMINAR ALUMNO
    fun quitarAlumnoDeAsignatura(idAlumno: Int?) {
        viewModel.quitarAlumno(idAsignatura, idAlumno)
    }
}
