package com.example.desafiohogwarts

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.Api.UsuarioCompartido
import com.example.desafiohogwarts.ViewModel.ViewModelLogin
import com.example.desafiohogwarts.databinding.ActivityLoginBinding
import com.example.desafiohogwarts.databinding.ActivitySuperAdminBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ⬇️ OBSERVER FIJO → escucha solo UNA VEZ (muy importante)
        viewModel.usuarioCompleto.observe(this) { usuario ->
            if (usuario != null) {

                // Guardar usuario
                UsuarioCompartido.usuario = usuario

                // Navegación según rol
                when (usuario.rol) {
                    4 -> startActivity(Intent(this, Activity_alumno::class.java))
                    3 -> startActivity(Intent(this, ActivityProfesor::class.java))
                    2 -> startActivity(Intent(this, ActivityAdmin::class.java))
                    1 -> startActivity(Intent(this, ActivityDios::class.java))
                }

                finish()
            }
        }

        // ⬇️ OBSERVER FIJO PARA ERRORES
        viewModel.error.observe(this) { msg ->
            msg?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btAceptar.setOnClickListener {
            val email = binding.idUsuario.text.toString()
            val pass = binding.idContraseA.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Introduce email y contraseña", Toast.LENGTH_SHORT).show()
            } else {

                // Hacemos login
                viewModel.login(email, pass)

                // Esperamos al usuario básico
                viewModel.usuario.observe(this) { user ->
                    if (user != null) {
                        Toast.makeText(this, "Login Correcto ${user.nombre}", Toast.LENGTH_SHORT).show()

                        // AHORA cargamos usuario completo con su rol
                        viewModel.cargarUsuarioPorEmail(user.email)
                    }
                }
            }
        }

        binding.btVolver.setOnClickListener {
            startActivity(Intent(this, InicioActivity::class.java))
            finish()
        }
    }
}
