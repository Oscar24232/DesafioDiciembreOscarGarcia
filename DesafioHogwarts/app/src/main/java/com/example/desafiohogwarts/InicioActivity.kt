package com.example.desafiohogwarts

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.desafiohogwarts.ViewModel.ViewModelLogin
import com.example.desafiohogwarts.databinding.ActivityInicioBinding
import kotlin.getValue

class InicioActivity: AppCompatActivity() {
    private lateinit var binding: ActivityInicioBinding
    private val viewModel: ViewModelLogin by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btRegis.setOnClickListener {
            val intent = Intent(this, ActivityDios::class.java)
            startActivity(intent)
        }
    }
}