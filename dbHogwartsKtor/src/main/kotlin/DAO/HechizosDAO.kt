package DAO

import modelo.Hechizo

interface HechizosDAO {
    fun getHechizos(): MutableList<Hechizo>
    fun aprenderHechizo(idHechizo: Int, idAlumno: Int): Boolean
    fun getHechizosAprendidos(idAlumno: Int): List<Int>
}