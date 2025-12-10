
interface AlumnoDAO {
    fun obtenerAsignaturasDeAlumno(idAlumno: Int): MutableList<Int>
    fun alumnoTieneAsignatura(idAlumno: Int, idAsignatura: Int): Boolean
}