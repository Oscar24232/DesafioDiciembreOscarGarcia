package com.example.desafiohogwarts.Adapters.Alumnos
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.databinding.ItemCardBinding
import com.example.desafiohogwarts.fragment.GestionUsuarios.GestionUsuarios
import modelo.Usuario

class UsuarioAdapter(
    private var listaUsuarios: MutableList<Usuario>,
    private val host: GestionUsuarios
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>(){

    class UsuarioViewHolder(val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]

        // Nombre
        holder.binding.txtNombreUsuario.text = usuario.nombre

        // Casa en formato texto
        holder.binding.txtCasaUsuario.text = when (usuario.id_casa) {
            1 -> "Gryffindor"
            2 -> "Hufflepuff"
            3 -> "Ravenclaw"
            4 -> "Slytherin"
            else -> "Sin casa"
        }



        // CLICK CORTO → mostrar detalle
        holder.itemView.setOnClickListener {
            DatosCompartidos.usuario = usuario
            val ctx = holder.itemView.context

            val usuarioSeleccion = """
                Nombre: ${usuario.nombre}
                Email: ${usuario.email}
                Casa: ${holder.binding.txtCasaUsuario.text}
                Nivel: ${usuario.nivel}
                Exp: ${usuario.exp}
                Estado: ${usuario.estado}
                Rol: ${usuario.rol}
            """.trimIndent()
            host.usuarioSeleccionado = usuario
            Toast.makeText(ctx, usuarioSeleccion, Toast.LENGTH_LONG).show()
        }

        // CLICK LARGO → borrar (llama a host)
        holder.itemView.setOnLongClickListener {
            val ctx = holder.itemView.context

            Toast.makeText(ctx, "Eliminando usuario ${usuario.email}…", Toast.LENGTH_SHORT).show()

            // El host ejecuta la lógica remota
            host.borrarUsuarioRemoto(usuario.email)

            true
        }
    }

    override fun getItemCount(): Int = listaUsuarios.size

    // Actualizar lista entera
    fun setUsuarios(nuevaLista: List<Usuario>) {
        listaUsuarios.clear()
        listaUsuarios.addAll(nuevaLista)
        notifyDataSetChanged()
    }

    // Eliminar por email (tu campo único)
    fun removeUsuarioPorEmail(email: String) {
        val idx = listaUsuarios.indexOfFirst { it.email == email }
        if (idx != -1) {
            listaUsuarios.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }
}

