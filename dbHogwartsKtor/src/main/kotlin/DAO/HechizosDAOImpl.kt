package DAO

import Helpers.Database
import modelo.Hechizo

class HechizosDAOImpl : HechizosDAO {

    override fun getHechizos(): MutableList<Hechizo> {
        val lista = mutableListOf<Hechizo>()
        val sql = "SELECT id, nombre, descripcion, experiencia_alumno FROM hechizo"
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                val rs = st.executeQuery()
                while (rs.next()) {
                    lista.add(Hechizo(
                        id = rs.getInt("id"),
                        nombre = rs.getString("nombre"),
                        descripcion = rs.getString("descripcion"),
                        experiencia_alumno = rs.getInt("experiencia_alumno")
                    ))
                }
            }
        }
        return lista
    }


    override fun aprenderHechizo(idHechizo: Int, idAlumno: Int): Boolean {
        var boolean=false
        val sqlInsert = """
        INSERT INTO alumno_hechizo (id_usuario, id_hechizo, fecha_aprendizaje)
        VALUES (?, ?, NOW())
    """.trimIndent()

        val sqlXP = """
        UPDATE usuarios 
        SET exp = exp + (SELECT experiencia_alumno FROM hechizo WHERE id = ?)
        WHERE id = ?
    """.trimIndent()

        Database.getConnection()?.use { conn ->
            try {
                conn.autoCommit = false

                // Insertar aprendizaje (si ya existe saltará error y se hará rollback)
                conn.prepareStatement(sqlInsert).use { st ->
                    st.setInt(1, idAlumno)
                    st.setInt(2, idHechizo)
                    st.executeUpdate()
                }

                // Sumar experiencia
                conn.prepareStatement(sqlXP).use { st ->
                    st.setInt(1, idHechizo)
                    st.setInt(2, idAlumno)
                    st.executeUpdate()
                }

                conn.commit()
                boolean=true

            } catch (e: Exception) {
                conn.rollback()
                boolean=false
            }
        }
        return boolean
    }
    override fun getHechizosAprendidos(idAlumno: Int): MutableList<Hechizo> {
        val lista = mutableListOf<Hechizo>()
        val sql = """
        SELECT h.id, h.nombre, h.descripcion, h.experiencia_alumno
        FROM hechizo h
        INNER JOIN alumno_hechizo ah ON ah.id_hechizo = h.id
        WHERE ah.id_usuario = ?
    """.trimIndent()
        println("➡️ getHechizosAprendidos() llamado con idAlumno = $idAlumno")
        println("➡️ SQL a ejecutar:\n$sql")
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idAlumno)
                val rs = st.executeQuery()
                while (rs.next()) {
                    lista.add(
                        Hechizo(
                            id = rs.getInt("id"),
                            nombre = rs.getString("nombre"),
                            descripcion = rs.getString("descripcion"),
                            experiencia_alumno = rs.getInt("experiencia_alumno")
                        )
                    )
                }
            }
        }
        return lista
    }


}
