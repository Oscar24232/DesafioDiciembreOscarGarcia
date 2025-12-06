package com.example.DAO

import com.example.modelo.ResultadoSombrero
import modelo.CasaAsignadaResponse
import modelo.PreferenciasCasa

interface SombreroDAO {
    fun asignarCasa(preferencias: PreferenciasCasa): CasaAsignadaResponse
}