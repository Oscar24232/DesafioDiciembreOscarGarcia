package com.example.desafiohogwarts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.desafiohogwarts.databinding.ActivityAlumnoBinding

class ActivityAdmin: AppCompatActivity() {
    private lateinit var binding: ActivityAlumnoBinding
    // private val viewModel: ViewModelLogin by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlumnoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}