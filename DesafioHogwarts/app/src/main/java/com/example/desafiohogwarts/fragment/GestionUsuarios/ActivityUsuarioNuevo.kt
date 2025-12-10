package com.example.desafiohogwarts.fragment.GestionUsuarios

import Api.UserNetwork
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.desafiohogwarts.databinding.ActivityNuevoUsuarioBinding
import kotlinx.coroutines.launch
import modelo.Usuario

class ActivityUsuarioNuevo : AppCompatActivity() {

    private lateinit var binding: ActivityNuevoUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNuevoUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // -------------------------
        // BOTÓN GUARDAR USUARIO NUEVO
        // -------------------------
        binding.btnGuardar.setOnClickListener {

            // Leer campos
            val nuevoUsuario = Usuario(
                nombre = binding.etNombre.text.toString(),
                email = binding.etEmail.text.toString(),
                contrasena = binding.etContrasena.text.toString(),
                foto = binding.etFoto.text.toString(),
                id_casa = binding.etCasa.text.toString().toIntOrNull() ?: 0,
                exp = binding.etExp.text.toString().toIntOrNull() ?: 0,
                nivel = binding.etNivel.text.toString().toIntOrNull() ?: 1,
                estado = if (binding.swEstado.isChecked) "1" else "0",
                rol = binding.etRol.text.toString().toIntOrNull() ?: 0
            )

            // Enviar a servidor Ktor
            lifecycleScope.launch {
                try {
                    val resp = UserNetwork.retrofit.insertarUsuario(nuevoUsuario)

                    if (resp.isSuccessful) {
                        Toast.makeText(
                            this@ActivityUsuarioNuevo,
                            "Usuario insertado con éxito",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ActivityUsuarioNuevo,
                            "Error al insertar usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@ActivityUsuarioNuevo,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        // -------------------------
        // BOTÓN CANCELAR
        // -------------------------
        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }
}
