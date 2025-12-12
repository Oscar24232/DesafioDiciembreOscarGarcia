package com.example.desafiohogwarts.Adapters.Alumnos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.databinding.ItemRankingCardBinding
import com.example.desafiohogwarts.modelo.Casa

class AdapterRankingCasas(
    private val lista: List<Casa>
) : RecyclerView.Adapter<AdapterRankingCasas.Holder>() {

    class Holder(val binding: ItemRankingCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRankingCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = lista[position]

        holder.binding.txtPosicion.text = "#${position + 1}"
        holder.binding.txtNombreCasa.text = item.nombre
        holder.binding.txtPuntosCasa.text = "${item.puntuacion} puntos"
        holder.binding.txtNumAlumnos.text = "Alumnos: ${item.num_alumnos}"
    }

    override fun getItemCount() = lista.size
}
