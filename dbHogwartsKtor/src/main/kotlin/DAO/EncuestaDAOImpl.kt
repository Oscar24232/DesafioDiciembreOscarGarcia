package com.example.DAO

import Helpers.Database
import com.example.modelo.Casa
import com.example.modelo.Encuesta

class EncuestaDAOImpl: HogwartsDAO {
    fun obtenerTodas(): MutableList<Encuesta> {
            val encuestas = mutableListOf<Encuesta>()
            val sql = "SELECT * FROM ENCUESTA"
            val connection = Database.getConnection()
            var encuesta: Encuesta? = null
            connection?.use {
                val statement = it.prepareStatement(sql)
                val resultSet = statement.executeQuery()

                while (resultSet.next()) {
                    println("🟢 [DAO] Encuesta encontrada con ID: ${resultSet.getInt("id")}")
                    encuesta = Encuesta(
                        id = resultSet.getInt("id"),
                        so = resultSet.getString("so"),
                        horas = resultSet.getInt("horas"),
                        ciclo = resultSet.getString("ciclo"),
                        modulos = resultSet.getString("modulos"),
                        dni_usuario = resultSet.getString("dni_usuario")
                    )
                    encuestas.add(encuesta)
                }
            }
        println("🟢 [DAO] Total encuestas leídas: ${encuestas.size}")
            return encuestas
    }
    fun insertar(encuesta: Encuesta): Boolean {
        println("🟢 Insertando encuesta: $encuesta")
        val sql = "INSERT INTO encuesta (so, horas, ciclo, modulos, dni_usuario) VALUES (?, ?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            println("🟢 Preparando statement...")
            statement.setString(1, encuesta.so)
            statement.setInt(2, encuesta.horas ?: 0)
            statement.setString(3, encuesta.ciclo)
            statement.setString(4, encuesta.modulos)
            statement.setString(5, encuesta.dni_usuario)
            val filas = statement.executeUpdate()
            return filas > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Casa> {
        TODO("Not yet implemented")
    }

    override fun obtener(nombre: String): Casa? {
        TODO("Not yet implemented")
    }
}

