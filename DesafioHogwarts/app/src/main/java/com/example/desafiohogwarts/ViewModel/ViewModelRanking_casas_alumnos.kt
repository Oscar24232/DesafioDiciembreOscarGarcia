package com.example.desafiohogwarts.ViewModel

import Api.UserNetwork
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafiohogwarts.modelo.Casa
import kotlinx.coroutines.launch

class ViewModelRanking_casas_alumnos : ViewModel() {

    private val _ranking = MutableLiveData<List<Casa>>()
    val ranking: LiveData<List<Casa>> = _ranking

    fun cargarRanking() {
        viewModelScope.launch {
            try {
                val resp = UserNetwork.retrofit.getRankingCasas()
                if (resp.isSuccessful && resp.body() != null) {
                    _ranking.value = resp.body()
                } else {
                    Log.d("API", "Error HTTP: ${resp.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Error ranking casas: ${e.message}")
            }
        }
    }
}
