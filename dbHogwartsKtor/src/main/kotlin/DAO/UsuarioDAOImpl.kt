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
        SELECT 
            id,
            nombre, 
            email, 
            contrasena, 
            foto, 
            id_casa, 
            exp, 
            nivel, 
            estado, 
            rol
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
                            id = rs.getInt("id"),
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
    SELECT 
        id,nombre,email,contrasena,foto,id_casa,exp,nivel,estado,rol FROM usuarios
    WHERE email = ?
    LIMIT 1
""".trimIndent()


        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setString(1, email)

                st.executeQuery().use { rs ->
                    if (rs.next()) {
                        return Usuario(
                            id = rs.getInt("id"),
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
    //OBTENER TODOS

    override fun obtenerTodosAlumnos(): MutableList<Usuario> {
        val lista = mutableListOf<Usuario>()
        val sql = """
        SELECT *
        FROM usuarios
        GROUP BY nombre
        ORDER BY nombre
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.executeQuery().use { rs ->
                    while (rs.next()) {
                        lista.add(
                            Usuario(
                                id = rs.getInt("id"),
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
                        )
                    }
                }
            }
        }

        return lista
    }
    override fun actualizarUsuario(usuario: Usuario): Boolean {
        val sql = """
            UPDATE usuarios SET
                nombre = ?,
                contrasena = ?,
                foto = ?,
                id_casa = ?,
                exp = ?,
                nivel = ?,
                estado = ?,
                rol = ?
            WHERE email = ?
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setString(1, usuario.nombre)
                st.setString(2, usuario.contrasena)
                st.setString(3, usuario.foto)
                st.setInt(4, usuario.id_casa)
                st.setInt(5, usuario.exp)
                st.setInt(6, usuario.nivel)
                st.setString(7, usuario.estado)
                st.setInt(8, usuario.rol)
                st.setString(9, usuario.email)

                return st.executeUpdate() > 0
            }
        }

        return false
    }

    // --------------------------------------------------------
    // BORRAR POR EMAIL
    // --------------------------------------------------------
    override fun borrarUsuario(email: String): Boolean {
        val sql = "DELETE FROM usuarios WHERE email = ?"

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setString(1, email)
                return st.executeUpdate() > 0
            }
        }

        return false
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

    override fun obtenerTodasLasCasas(): List<Casa> {
        TODO("Not yet implemented")
    }


}