package com.example.desafiohogwarts.Adapters.Profesor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.ActivityProfesor
import com.example.desafiohogwarts.databinding.ItemCardAsignaturaBinding
import com.example.desafiohogwarts.modelo.Asignaturas

class AdapterAsignaturasProfesor(
    private var listaAsignaturas: MutableList<Asignaturas>,
    private val host: ActivityProfesor
) : RecyclerView.Adapter<AdapterAsignaturasProfesor.Holder>() {

    class Holder(val binding: ItemCardAsignaturaBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCardAsignaturaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = listaAsignaturas[position]

        holder.binding.tvAsignaturaNombre .text = a.nombre
        Log.d("AsignaProfesor", "${a.id_asignatura} ${a.nombre}")
        holder.binding.root.setOnClickListener {
            host.irDetalleAsignatura(a)
        }


    }

    override fun getItemCount(): Int = listaAsignaturas.size

    // Métodos de actualización del adapter
    fun actualizarLista(nuevaLista: List<Asignaturas>) {
        listaAsignaturas.clear()
        listaAsignaturas.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
