package DAO

import com.example.desafiohogwarts.modelo.Asignaturas
import modelo.Usuario

interface ProfesorDAO {

    fun getAsignaturasDelProfesor(idProfesor: Int): MutableList<Asignaturas>

    fun getAlumnosDeAsignatura(idAsignatura: Int): MutableList<Usuario>

    fun existeRelacionAlumnoAsignatura(idAlumno: Int, idAsignatura: Int): Boolean

    fun insertAlumnoEnAsignatura(idAlumno: Int, idAsignatura: Int): Boolean

    fun eliminarAlumnoDeAsignatura(idAlumno: Int, idAsignatura: Int): Boolean
}
