import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.desafiohogwarts.ActivityProfesor
import com.example.desafiohogwarts.databinding.ItemCardAsignaturaBinding
import com.example.desafiohogwarts.modelo.Asignaturas



class AsignaturaAdapter(
    private var listaAsignaturas: MutableList<Asignaturas>,
    private val host: ActivityProfesor
) : RecyclerView.Adapter<AsignaturaAdapter.Holder>() {

    inner class Holder(val binding: ItemCardAsignaturaBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemCardAsignaturaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val asignatura = listaAsignaturas[position]

        holder.binding.tvAsignaturaNombre.text = asignatura.nombre

        holder.binding.root.setOnClickListener {
            host.irDetalleAsignatura(asignatura)
        }

    }

    override fun getItemCount(): Int = listaAsignaturas.size

    // 🔹 Igual que en tus adapters: método para actualizar listas
    fun actualizarLista(nuevaLista: List<Asignaturas>) {
        listaAsignaturas.clear()
        listaAsignaturas.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
