package com.example.DAO

import com.example.modelo.Casa
import com.example.modelo.ResultadoSombrero
import modelo.CasaAsignadaResponse
import modelo.PreferenciasCasa

interface SombreroDAO {
    fun asignarCasa(preferencias: PreferenciasCasa): CasaAsignadaResponse
    fun obtener_sombrero(nombre: String): Casa?
}