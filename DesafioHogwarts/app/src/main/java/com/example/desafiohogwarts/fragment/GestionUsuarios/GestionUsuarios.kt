package com.example.desafiohogwarts.fragment.GestionUsuarios

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.ViewModel.GestionUsuariosViewModel
import com.example.desafiohogwarts.databinding.FragmentGestionUsuariosBinding
import com.example.desafiohogwarts.Adapters.UsuarioAdapter
import modelo.Usuario

class GestionUsuarios : Fragment() {

    private var _binding: FragmentGestionUsuariosBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GestionUsuariosViewModel by viewModels()

    private lateinit var adaptador: UsuarioAdapter
    private val listaLocal = ArrayList<Usuario>()

    // Lista completa para que el buscador funcione de verdad
    private var listaCompleta = listOf<Usuario>()

    //recojo el usuario que quiero editar
    var usuarioSeleccionado: Usuario? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGestionUsuariosBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // CONFIGURAR ADAPTER

        adaptador = UsuarioAdapter(listaLocal, this)
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerUsuarios.adapter = adaptador

        viewModel.adapter = adaptador // para borrar desde ViewModel


        // OBSERVAR LA LISTA

        viewModel.usuarioList.observe(viewLifecycleOwner) { usuarios ->
            listaCompleta = usuarios          // ← Guardamos lista original
            adaptador.setUsuarios(usuarios)   // ← Mostramos lista completa
        }


        // EL BUSCADOR

        binding.etBuscarUsuario.addTextChangedListener { texto ->
            val query = texto.toString().trim().lowercase()

            val filtrados = listaCompleta.filter { usuario ->
                usuario.nombre.lowercase().contains(query)
            }

            adaptador.setUsuarios(filtrados)
        }
        binding.btEditar.setOnClickListener {

            if (usuarioSeleccionado == null) {
                Toast.makeText(requireContext(), "Selecciona un usuario primero", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(requireContext(), ActivityEditarUsuario::class.java)
            intent.putExtra("usuario", usuarioSeleccionado) // ← LO PASAMOS
            startActivity(intent)
        }
        binding.btNuevo.setOnClickListener {
            val intent = Intent(requireContext(), ActivityUsuarioNuevo::class.java)
            startActivity(intent)
        }



        // CARGAR USUARIOS

        viewModel.cargarUsuarios()
    }



    // ESTA FUNCIÓN LA LLAMA EL ADAPTER

    fun borrarUsuarioRemoto(email: String) {
        viewModel.borrarUsuario(email)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
