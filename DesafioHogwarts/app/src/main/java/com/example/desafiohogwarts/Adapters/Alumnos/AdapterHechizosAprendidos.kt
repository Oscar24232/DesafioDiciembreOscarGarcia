package com.example.desafiohogwarts.Adapters.Alumnos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.databinding.ItemHechizoBinding
import com.example.desafiohogwarts.modelo.Hechizo

class AdapterHechizosAprendidos(
    private var lista: MutableList<Hechizo>
) : RecyclerView.Adapter<AdapterHechizosAprendidos.Holder>() {

    class Holder(val binding: ItemHechizoBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemHechizoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val h = lista[position]

        holder.binding.txtNombreHechizo.text = h.nombre
        holder.binding.txtDescHechizo.text = h.descripcion
        holder.binding.txtPuntosHechizo.text = "Puntos: ${h.experiencia_alumno}"

        //Ocultar botón aprender
        holder.binding.btnAprender.visibility = View.GONE
    }

    override fun getItemCount(): Int = lista.size
}

