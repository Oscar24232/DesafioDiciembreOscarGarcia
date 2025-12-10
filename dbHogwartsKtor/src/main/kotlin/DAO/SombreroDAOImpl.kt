package com.example.DAO

import Helpers.Database
import com.example.modelo.Casa
import modelo.CasaAsignadaResponse
import modelo.PreferenciasCasa
import kotlin.use

class SombreroDAOImpl : SombreroDAO {
    var hogwartsCasas = HogwartsDAOImpl()

        override fun asignarCasa(preferencias: PreferenciasCasa): CasaAsignadaResponse {

            // 1. Cargar todas las casas
            val lista = hogwartsCasas.obtenerTodos()

            // Si algo falla → fallback
            if (lista.isEmpty()) return CasaAsignadaResponse(1)

            // 2. Casa con menos alumnos
            val casaMenosAlumnos = lista.minByOrNull { it.num_alumnos }!!

            // 3. Calcular promedio
            val promedio = lista.sumOf { it.num_alumnos } / lista.size.toDouble()

            // 4. Si está MUY por debajo del promedio → se favorece esa casa
            val porcentaje = (casaMenosAlumnos.num_alumnos / promedio) * 100
            if (porcentaje < 85) {
                return CasaAsignadaResponse(casaMenosAlumnos.id_casa)
            }

            // ---------------------------
            // 5. Mirar preferencias
            // ---------------------------

            val prefs = preferencias.preferencias
            val valorMax = prefs.maxOf { it.value }

            // Casas que tienen la puntuación más alta
            val favoritas = prefs.filter { it.value == valorMax }.keys


            // 6. Si solo hay UNA favorita → esa
            if (favoritas.size == 1) {

                val nombre = favoritas.first()
                val casa = obtener_sombrero(nombre)

                return CasaAsignadaResponse(
                    casa?.id_casa ?: 1
                )
            }

            // 7. Si hay EMPATE → random
            val randomNombre = favoritas.random()
            val casaRandom = obtener_sombrero(randomNombre)

            return CasaAsignadaResponse(
                casaRandom?.id_casa ?: 1
            )
        }
    //funcion del sombrero
    override fun obtener_sombrero(nombre: String): Casa? {
        val sql = "SELECT * FROM casas WHERE nombre = ?"
        val connection = Database.getConnection()
        var casa: Casa? = null

        println("🟢 [DAO] Buscando casa con nombre: $nombre")

        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, nombre)
            val resultSet = statement.executeQuery()

            if (resultSet.next()) {
                casa = Casa(
                    id_casa = resultSet.getInt("id_casa"),
                    nombre = resultSet.getString("nombre"),
                    puntuacion = resultSet.getInt("puntuacion"),
                    num_alumnos = resultSet.getInt("num_alumnos")
                )
                println("✅ [DAO] Casa encontrada: $casa")
            } else {
                println("⚠️ [DAO] No se encontró la casa con nombre: $nombre")
            }
        }

        return casa
    }
    }
