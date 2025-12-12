package com.example.desafiohogwarts

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.desafiohogwarts.Adapters.Alumnos.AdapterRankingCasas
import com.example.desafiohogwarts.ViewModel.ViewModelRanking_casas_alumnos
import com.example.desafiohogwarts.databinding.ActivityRankingCasasBinding

class ActivityRankingCasas : AppCompatActivity() {

    private lateinit var binding: ActivityRankingCasasBinding
    private val vm: ViewModelRanking_casas_alumnos by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRankingCasasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvRanking.layoutManager = LinearLayoutManager(this)

        vm.ranking.observe(this) { lista ->
            binding.rvRanking.adapter = AdapterRankingCasas(lista)
        }

        vm.cargarRanking()

        binding.btnVolver.setOnClickListener { finish() }
    }
}
