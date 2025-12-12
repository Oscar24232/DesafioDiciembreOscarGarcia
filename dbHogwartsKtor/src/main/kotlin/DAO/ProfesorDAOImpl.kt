import DAO.ProfesorDAO
import Helpers.Database
import com.example.desafiohogwarts.modelo.Asignaturas
import modelo.Usuario


class ProfesorDAOImpl : ProfesorDAO {


    //OBTENER ASIGNATURAS DE UN PROFESOR
    override fun getAsignaturasDelProfesor(idProfesor: Int): MutableList<Asignaturas> {
        val lista = mutableListOf<Asignaturas>()

        val sql = """
            SELECT a.id_asignatura, a.nombre
            FROM profesor_asignatura pa
            INNER JOIN asignaturas a ON pa.id_asignatura = a.id_asignatura
            WHERE pa.id_profesor = ?
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idProfesor)
                val rs = st.executeQuery()

                while (rs.next()) {
                    lista.add(
                        Asignaturas(
                            id_asignatura = rs.getInt("id_asignatura"),
                            nombre = rs.getString("nombre")
                        )
                    )
                }
            }
        }

        return lista
    }

    //OBTENER ALUMNOS DE UNA ASIGNATURA
    override fun getAlumnosDeAsignatura(idAsignatura: Int): MutableList<Usuario> {
        val lista = mutableListOf<Usuario>()

        val sql = """
        SELECT u.id, u.nombre, u.email, u.contrasena, u.foto,
               u.id_casa, u.exp, u.nivel, u.estado, u.rol
        FROM alumno_asignatura aa
        INNER JOIN usuarios u ON aa.id_usuario = u.id
        WHERE aa.id_asignatura = ?
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idAsignatura)
                val rs = st.executeQuery()

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
                            estado = rs.getInt("estado"),
                            rol = rs.getInt("rol")
                        )
                    )
                }
            }
        }

        return lista
    }




    //COMPROBAR SI UN ALUMNO YA ESTÁ EN LA ASIGNATURA

    override fun existeRelacionAlumnoAsignatura(idAlumno: Int, idAsignatura: Int): Boolean {
        var existe = false

        val sql = """
            SELECT COUNT(*) AS total
            FROM alumno_asignatura
            WHERE id_usuario = ? AND id_asignatura = ?
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idAlumno)
                st.setInt(2, idAsignatura)

                val rs = st.executeQuery()
                if (rs.next()) {
                    existe = rs.getInt("total") > 0
                }
            }
        }

        return existe
    }



    //INSERTAR ALUMNO EN ASIGNATURA

    override fun insertAlumnoEnAsignatura(idAlumno: Int, idAsignatura: Int): Boolean {
        var boolean = false

        val sql = """
            INSERT INTO alumno_asignatura (id_usuario, id_asignatura)
            VALUES (?, ?)
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            try {
                conn.prepareStatement(sql).use { st ->
                    st.setInt(1, idAlumno)
                    st.setInt(2, idAsignatura)
                    st.executeUpdate()
                }
                boolean = true
            } catch (e: Exception) {
                boolean = false
            }
        }

        return boolean
    }


    override fun eliminarAlumnoDeAsignatura(idAlumno: Int, idAsignatura: Int): Boolean {
        var boolean = false

        val sql = """
            DELETE FROM alumno_asignatura
            WHERE id_usuario = ? AND id_asignatura = ?
        """.trimIndent()

        Database.getConnection()?.use { conn ->
            try {
                conn.prepareStatement(sql).use { st ->
                    st.setInt(1, idAlumno)
                    st.setInt(2, idAsignatura)
                    st.executeUpdate()
                }
                boolean = true
            } catch (e: Exception) {
                boolean = false
            }
        }

        return boolean
    }
}
