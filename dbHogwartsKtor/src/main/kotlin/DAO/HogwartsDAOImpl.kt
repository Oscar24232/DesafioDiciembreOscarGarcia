package com.example.DAO

import Helpers.Database
import com.example.modelo.Casa
import com.example.modelo.Encuesta
import com.example.modelo.Persona
import kotlin.use

class HogwartsDAOImpl:HogwartsDAO {
        override fun obtenerTodos(): List<Casa> {
            val casas = mutableListOf<Casa>()
            val sql = "SELECT * FROM casas"
            val connection = Database.getConnection()
            connection?.use {
                val statement = it.prepareStatement(sql)
                val resultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val casa = Casa(
                        nombre = resultSet.getString("nombre"),
                        puntuacion = resultSet.getInt("puntuacion"),
                    )
                    casas.add(casa)
                }
            }
            return casas
        }
    override fun obtener(nombre: String): Casa? {
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
                    nombre = resultSet.getString("nombre"),
                    puntuacion = resultSet.getInt("puntuacion"),
                )
                println("✅ [DAO] Casa encontrada: $casa")
            } else {
                println("⚠️ [DAO] No se encontró la casa con nombre: $nombre")
            }
        }

        return casa
    }

}

