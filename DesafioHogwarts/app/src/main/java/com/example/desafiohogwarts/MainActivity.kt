package com.example.desafiohogwarts

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.desafiohogwarts.ViewModel.ViewModelCasa
import com.example.desafiohogwarts.databinding.ActivityCasaPuntosBinding
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCasaPuntosBinding
    private val viewModel: ViewModelCasa by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCasaPuntosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel.casa.observe(this) { casa ->
            if (casa != null) {

                binding.idNombre.setText(casa.nombre)
               // if (binding.idNombre.text.equals("GRYFFINDOR")) binding.imageView4.setImageDrawable(R.drawable.img_1)
                binding.idPuntuacion.setText(casa.puntuacion.toString())
            } else {
                Toast.makeText(this, "No se encontró la casa", Toast.LENGTH_LONG).show()
            }
        }
        lifecycleScope.launch {
            val casa = viewModel.cargarPorCasa("GRYFFINDOR")
            Log.d("CASA", "Casa recibida: $casa")
        }

    }
}

