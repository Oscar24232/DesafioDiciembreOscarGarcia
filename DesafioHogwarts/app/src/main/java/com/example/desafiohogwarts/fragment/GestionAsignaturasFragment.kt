package com.example.desafiohogwarts

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.desafiohogwarts.ViewModel.GestionAsignaturasViewModel

class GestionAsignaturasFragment : Fragment(R.layout.fragment_gestion_asignaturas) {

    companion object {
        fun newInstance() = GestionAsignaturasFragment()
    }

    private val viewModel: GestionAsignaturasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_gestion_asignaturas, container, false)
    }
}