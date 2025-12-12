package com.example.desafiohogwarts.fragment.GestionUsuarios

import Api.UserNetwork
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.desafiohogwarts.Api.DatosCompartidos
import com.example.desafiohogwarts.databinding.ActivityEditarUsuarioBinding
import kotlinx.coroutines.launch
import modelo.Usuario

class ActivityEditarUsuario : AppCompatActivity() {

    private lateinit var binding: ActivityEditarUsuarioBinding
    private lateinit var usuario: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir usuario CORRECTAMENTE
        usuario = DatosCompartidos.usuario


        binding.etCasa.setText(usuario.id_casa.toString())
        binding.etExp.setText(usuario.exp.toString())
        binding.etRol.setText(usuario.rol.toString())
        binding.etContrasena.setText(usuario.contrasena)
        binding.etFoto.setText(usuario.foto)
        binding.etNivel.setText(usuario.nivel.toString())
        binding.etEmail.setText(usuario.email)
        binding.etNombre.setText(usuario.nombre)
        binding.swEstado.isChecked = usuario.estado == "1"



        // BOTÓN GUARDAR
        binding.btnGuardar.setOnClickListener {

            val actualizado = Usuario(
                nombre = binding.etNombre.text.toString(),
                email = usuario.email, // NO SE CAMBIA
                contrasena = binding.etContrasena.text.toString(),
                foto = binding.etFoto.text.toString(),
                id_casa = binding.etCasa.text.toString().toInt(),
                exp = binding.etExp.text.toString().toInt(),
                nivel = binding.etNivel.text.toString().toInt(),
                estado = if (binding.swEstado.isChecked) "1" else "0",
                rol = binding.etRol.text.toString().toInt()
            )

            lifecycleScope.launch {
                try {
                    val resp = UserNetwork.retrofit.actualizarUsuario(actualizado)

                    if (resp.isSuccessful) {
                        Toast.makeText(
                            this@ActivityEditarUsuario,
                            "Usuario actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        Toast.makeText(
                            this@ActivityEditarUsuario,
                            "Error al actualizar usuario",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(
                        this@ActivityEditarUsuario,
                        "Error de conexión con el servidor",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


        binding.btnCancelar.setOnClickListener {
            finish()
        }
    }
}