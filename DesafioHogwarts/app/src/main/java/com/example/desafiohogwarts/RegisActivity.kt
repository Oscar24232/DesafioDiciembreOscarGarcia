package com.example.desafiohogwarts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.ViewModel.ViewModelLogin
import com.example.desafiohogwarts.databinding.ActivityRegisBinding

class RegisActivity: AppCompatActivity() {
        private lateinit var binding: ActivityRegisBinding
        private val viewModel: ViewModelLogin by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityRegisBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // botón iniciar sesión
            binding.btAceptar.setOnClickListener {
                val email = binding.idUsuario.text.toString().trim()
                val pass = binding.idContraseA.text.toString().trim()

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Introduce email y contraseña", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.login(email, pass)
                }
            }
            binding.btVolver.setOnClickListener {
                val intent = Intent(this, InicioActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }
            // Observa respuesta del login
            viewModel.usuario.observe(this) { user ->
                if (user != null) {
                    Toast.makeText(this, "Bienvenido ${user.nombre}", Toast.LENGTH_LONG).show()

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            // Observa errores
            viewModel.error.observe(this) { msg ->
                msg?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
}