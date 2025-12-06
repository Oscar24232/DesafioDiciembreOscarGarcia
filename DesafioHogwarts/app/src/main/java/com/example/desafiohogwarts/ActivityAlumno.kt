package com.example.desafiohogwarts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.ViewModel.ViewModelLogin
import com.example.desafiohogwarts.databinding.ActivityAlumnoBinding
import com.example.desafiohogwarts.databinding.ActivityLoginBinding
import kotlin.getValue

class ActivityAlumno: AppCompatActivity() {
    private lateinit var binding: ActivityAlumnoBinding
   // private val viewModel: ViewModelLogin by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}