package com.example.desafiohogwarts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.ViewModel.ViewModelLogin
import com.example.desafiohogwarts.databinding.ActivityLoginBinding

class LoginActivity: AppCompatActivity() {


        private lateinit var binding: ActivityLoginBinding
        private val viewModel: ViewModelLogin by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btAceptar.setOnClickListener {
                val email = binding.idUsuario.text.toString()
                val pass = binding.idContraseA.text.toString()

                if (email.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(this, "Introduce email y contraseña", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.login(email, pass)
                    // LOGIN OK → ABRE HOME
                    viewModel.usuario.observe(this) { user ->
                        if (user != null) {
                            Toast.makeText(this,"Login Correcto ${user.nombre}",Toast.LENGTH_SHORT).show()
                            // Llamamos para obtener el usuario COMPLETO con su rol
                            viewModel.cargarUsuarioPorEmail(user.email)
                            viewModel.usuarioCompleto.observe(this) { usuario ->
                                if (usuario != null) {
                                    when (usuario.rol) {
                                        4 -> startActivity(Intent(this, ActivityAlumno::class.java))
                                        3 -> startActivity(Intent(this, ActivityProfesor::class.java))
                                        2 -> startActivity(Intent(this, ActivityAdmin::class.java))
                                        1 -> startActivity(Intent(this, ActivityDios::class.java))
                                    }
                                    finish()
                                }
                            }


                        }
                    }
                }
                    // ERRORES
                    viewModel.error.observe(this) { msg ->
                        msg?.let {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            binding.btVolver.setOnClickListener {
                val intent = Intent(this, InicioActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
}
