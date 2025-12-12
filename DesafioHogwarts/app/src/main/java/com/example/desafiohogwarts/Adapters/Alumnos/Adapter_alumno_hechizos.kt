package com.example.desafiohogwarts.Adapters.Alumnos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.ActivityHechizosAlumno
import com.example.desafiohogwarts.databinding.ItemHechizoBinding
import com.example.desafiohogwarts.modelo.Hechizo

class Adapter_alumno_hechizos(
    private var listaHechizos: MutableList<Hechizo>,
    private var listaAprendidos: List<Int>,
    private val host: ActivityHechizosAlumno
) : RecyclerView.Adapter<Adapter_alumno_hechizos.Holder>() {

    class Holder(val binding: ItemHechizoBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemHechizoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val h = listaHechizos[position]

        holder.binding.txtNombreHechizo.text = h.nombre
        holder.binding.txtDescHechizo.text = h.descripcion
        holder.binding.txtPuntosHechizo.text = "Puntos: ${h.experiencia_alumno}"

        val yaAprendido = listaAprendidos.contains(h.id)

        holder.binding.btnAprender.visibility =
            if (yaAprendido) View.GONE else View.VISIBLE

        if (!yaAprendido) {
            holder.binding.btnAprender.setOnClickListener {
                host.aprenderHechizo(h.id!!)
            }
        }
    }

    override fun getItemCount(): Int = listaHechizos.size

    //METODOS DE ACTUALIZACION DEL ADAPTER
    fun actualizarHechizos(listaNueva: List<Hechizo>) {
        listaHechizos.clear()
        listaHechizos.addAll(listaNueva)
        notifyDataSetChanged()
    }

    fun actualizarAprendidos(listaNueva: List<Int>) {
        listaAprendidos = listaNueva
        notifyDataSetChanged()
    }
}