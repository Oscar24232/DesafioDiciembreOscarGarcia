package com.example.desafiohogwarts.Adapters.Profesor

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.AsignaturaDetailActivity
import com.example.desafiohogwarts.databinding.ItemAlumnoProfesorBinding
import modelo.Usuario

class AdapterAlumnosAsignatura(
    private var listaAlumnos: MutableList<Usuario>,
    private val host: AsignaturaDetailActivity
) : RecyclerView.Adapter<AdapterAlumnosAsignatura.Holder>() {

    class Holder(val binding: ItemAlumnoProfesorBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemAlumnoProfesorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val alumno = listaAlumnos[position]

        // NOMBRE
        holder.binding.tvAlumnoNombre.text = alumno.nombre

        // EXP
        holder.binding.tvAlumnoExp.text = "Exp: ${alumno.exp}"

        // CASA
        val nombreCasa = when (alumno.id_casa) {
            1 -> "Gryffindor"
            2 -> "Hufflepuff"
            3 -> "Ravenclaw"
            4 -> "Slytherin"
            else -> "Sin casa"
        }

        holder.binding.tvAlumnoCasa.text = "Casa: $nombreCasa"

        // BOTÓN ELIMINAR
        holder.binding.btnEliminarAlumno.setOnClickListener {
            host.quitarAlumnoDeAsignatura(alumno.id)
        }
    }

    override fun getItemCount(): Int = listaAlumnos.size

    //Actualizar lista completa
    fun actualizarLista(nueva: List<Usuario>) {
        listaAlumnos.clear()
        listaAlumnos.addAll(nueva)
        notifyDataSetChanged()
    }

}
