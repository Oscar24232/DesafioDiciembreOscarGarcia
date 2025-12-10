package com.example.DAO

import AlumnoDAO
import Helpers.Database

class AlumnoDAOImpl: AlumnoDAO {

    override fun obtenerAsignaturasDeAlumno(idAlumno: Int): MutableList<Int> {
        val lista = mutableListOf<Int>()
        val sql = "SELECT id_asignatura FROM alumno_asignatura WHERE id_usuario = ?"

        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idAlumno)

                val rs = st.executeQuery()
                while (rs.next()) {
                    lista.add(rs.getInt("id_asignatura"))
                }
            }
        }
        return lista
    }
    override fun alumnoTieneAsignatura(idAlumno: Int, idAsignatura: Int): Boolean {
        val sql = "SELECT 1 FROM alumno_asignatura WHERE id_usuario = ? AND id_asignatura = ? LIMIT 1"
        Database.getConnection()?.use { conn ->
            conn.prepareStatement(sql).use { st ->
                st.setInt(1, idAlumno)
                st.setInt(2, idAsignatura)
                val rs = st.executeQuery()
                return rs.next()
            }
        }
        return false
    }
}