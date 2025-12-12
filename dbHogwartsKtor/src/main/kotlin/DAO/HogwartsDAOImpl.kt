package com.example.DAO

import Helpers.Database
import com.example.modelo.Casa
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
                        id_casa = resultSet.getInt("id_casa"),
                        nombre = resultSet.getString("nombre"),
                        puntuacion = resultSet.getInt("puntuacion"),
                        num_alumnos = resultSet.getInt("num_alumnos")
                    )
                    casas.add(casa)
                }
            }
            return casas
        }
        override fun obtenerPorNombre(nombre: String): Casa? {
            val sql = "SELECT * FROM casas WHERE LOWER(nombre) = LOWER(?) LIMIT 1"
            var casa: Casa? = null

                Database.getConnection()?.use { conn ->
                conn.prepareStatement(sql).use { st ->
                st.setString(1, nombre)

                val rs = st.executeQuery()
                    if (rs.next()) {
                        casa = Casa(
                            id_casa = rs.getInt("id_casa"),
                            nombre = rs.getString("nombre"),
                            puntuacion = rs.getInt("puntuacion"),
                            num_alumnos = rs.getInt("num_alumnos")
                        )
                    }
                }
            }
            return casa
        }

    override fun obtenerPorId(id: Int): Casa? {
        val sql = "SELECT id_casa, nombre, puntuacion, num_alumnos FROM casas WHERE id_casa = ? LIMIT 1"

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, id)

                st.executeQuery().use { rs ->
                    if (rs.next()) {
                        return Casa(
                            id_casa = rs.getInt("id_casa"),
                            nombre = rs.getString("nombre"),
                            puntuacion = rs.getInt("puntuacion"),
                            num_alumnos = rs.getInt("num_alumnos")
                        )
                    }
                }
            }
        }
        return null
    }
    override fun getRankingCasas(): MutableList<Casa> {
        val lista = mutableListOf<Casa>()

        val sql = """
        SELECT id_casa, nombre, puntuacion, num_alumnos
        FROM casas
        ORDER BY puntuacion DESC
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                val rs = st.executeQuery()
                while (rs.next()) {
                    lista.add(
                        Casa(
                            id_casa = rs.getInt("id_casa"),
                            nombre = rs.getString("nombre"),
                            puntuacion = rs.getInt("puntuacion"),
                            num_alumnos = rs.getInt("num_alumnos")
                        )
                    )
                }
            }
        }

        return lista
    }

}

