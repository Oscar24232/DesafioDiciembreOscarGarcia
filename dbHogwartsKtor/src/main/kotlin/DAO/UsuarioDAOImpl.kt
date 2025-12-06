package DAO

import Helpers.Database
import com.example.modelo.Casa
import com.example.modelo.Persona
import modelo.Usuario


class UsuarioDAOImpl : UsuarioDAO {
    override fun existeEmail(email: String): Boolean {
        val sql = "SELECT 1 FROM usuarios WHERE email = ? LIMIT 1"
        Database.getConnection()?.use { conn ->
            val st = conn.prepareStatement(sql)
            st.setString(1, email)
            val rs = st.executeQuery()
            return rs.next()
        }
        return false
    }
    override fun login(email: String, contrasena: String): Usuario? {
        val sql = """
        SELECT nombre, email, contrasena, foto, id_casa, exp, nivel, estado, rol
        FROM usuarios
        WHERE email = ? AND contrasena = ?
        LIMIT 1
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setString(1, email)
                st.setString(2, contrasena)

                st.executeQuery().use { rs ->
                    if (rs.next()) {
                        return Usuario(
                            nombre = rs.getString("nombre"),
                            email = rs.getString("email"),
                            contrasena = rs.getString("contrasena"),
                            foto = rs.getString("foto"),
                            id_casa = rs.getInt("id_casa"),
                            exp = rs.getInt("exp"),
                            nivel = rs.getInt("nivel"),
                            estado = rs.getString("estado"),
                            rol = rs.getInt("rol")
                        )
                    }
                }
            }
        }
        return null
    }

    override fun getUsuarioByEmail(email: String): Usuario? {
        val sql = """
        SELECT nombre, email, contrasena, foto, id_casa, exp, nivel, estado, rol
        FROM usuarios
        WHERE email = ?
        LIMIT 1
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setString(1, email)

                st.executeQuery().use { rs ->
                    if (rs.next()) {
                        return Usuario(
                            nombre = rs.getString("nombre"),
                            email = rs.getString("email"),
                            contrasena = rs.getString("contrasena"),
                            foto = rs.getString("foto"),
                            id_casa = rs.getInt("id_casa"),
                            exp = rs.getInt("exp"),
                            nivel = rs.getInt("nivel"),
                            estado = rs.getString("estado"),
                            rol = rs.getInt("rol")
                        )
                    }
                }
            }
        }

        return null
    }



    override fun insertar(usuario: Usuario): Boolean {
        val sql = """
            INSERT INTO usuarios (nombre,email,contrasena,foto,id_casa,exp,nivel,estado,rol)
            VALUES (?,?,?,?,?,?,?,?,?)
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            val st = conn.prepareStatement(sql)
            st.setString(1, usuario.nombre)
            st.setString(2, usuario.email)
            st.setString(3, usuario.contrasena)
            st.setString(4, usuario.foto)
            st.setInt(5, usuario.id_casa)
            st.setInt(6, usuario.exp)
            st.setInt(7, usuario.nivel)
            st.setString(8, usuario.estado)
            st.setInt(9,usuario.rol)

            return st.executeUpdate() > 0
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