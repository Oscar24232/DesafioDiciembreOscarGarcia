package com.example.desafiohogwarts.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.ActivityHechizosAlumno
import com.example.desafiohogwarts.databinding.ItemHechizoBinding
import com.example.desafiohogwarts.modelo.Hechizo

class Adapter_alumno_hechizos(
    private var listaHechizos: MutableList<Hechizo>,
    private val host: ActivityHechizosAlumno
) : RecyclerView.Adapter<Adapter_alumno_hechizos.HechizoViewHolder>() {

    class HechizoViewHolder(val binding: ItemHechizoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HechizoViewHolder {
        val binding = ItemHechizoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HechizoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HechizoViewHolder, position: Int) {
        val hechizo = listaHechizos[position]

        // Poner datos del hechizo
        holder.binding.txtNombreHechizo.text = hechizo.nombre
        holder.binding.txtDescHechizo.text = hechizo.descripcion
        holder.binding.txtPuntosHechizo.text = "Puntos: ${hechizo.experiencia_alumno}"

        // BOTÓN APRENDER
        holder.binding.btnAprender.setOnClickListener {
            host.aprenderHechizo(hechizo.id)
        }

    }

    override fun getItemCount(): Int = listaHechizos.size

    // Método para actualizar la lista desde el ViewModel
    fun setHechizos(nuevaLista: List<Hechizo>) {
        listaHechizos.clear()
        listaHechizos.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}