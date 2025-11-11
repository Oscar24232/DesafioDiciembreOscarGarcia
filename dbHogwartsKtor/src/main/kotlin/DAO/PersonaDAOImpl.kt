package DAO

import Helpers.Database
import com.example.modelo.Casa
import com.example.modelo.Persona


class PersonaDAOImpl : PersonaDAO {
    override fun insertar(persona: Persona): Boolean {
        val sql = "INSERT INTO USUARIOS (dni, nombre, clave, tfno) VALUES (?, ?, ?, ?)"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, persona.dni)
            statement.setString(2, persona.nombre)
            statement.setString(3, persona.clave)
            statement.setString(4, persona.tfno)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtener(dni: String): Persona? {
        TODO("Not yet implemented")
    }


    override fun actualizar(persona: Persona): Boolean {
        val sql = "UPDATE USUARIOS SET nombre = ?, clave = ?, tfno = ? WHERE dni = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, persona.nombre)
            statement.setString(2, persona.clave)
            statement.setString(3, persona.tfno)
            statement.setString(4, persona.dni)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun eliminar(dni: String): Boolean {
        val sql = "DELETE FROM USUARIOS WHERE dni = ?"
        val connection = Database.getConnection()
        connection?.use {
            val statement = it.prepareStatement(sql)
            statement.setString(1, dni)

            return statement.executeUpdate() > 0
        }
        return false
    }

    override fun obtenerTodos(): List<Casa> {
        TODO("Not yet implemented")
    }


}