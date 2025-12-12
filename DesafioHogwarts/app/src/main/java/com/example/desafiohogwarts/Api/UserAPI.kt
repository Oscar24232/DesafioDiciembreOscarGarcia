package Api

import com.example.desafiohogwarts.modelo.Asignaturas
import com.example.desafiohogwarts.modelo.Casa
import com.example.desafiohogwarts.modelo.CasaAsignada
import com.example.desafiohogwarts.modelo.Hechizo
import com.example.desafiohogwarts.modelo.PreferenciasCasa
import modelo.UsuarioLogin
import modelo.Respuesta
import modelo.Usuario
import retrofit2.Response
import retrofit2.http.*

/**
 * Interfaz de métodos que nos permitirán lanzar peticiones a la API.
 * Esta interfaz la usamos tanto para el modo con ViewModel como el modo sin ViewNodel. Se han separado con un comentario los métodos.
 */
interface UserAPI {

    /*
    Métodos usados para el acceso con ViewModel.
     */
    //para dar el usuario completo
    @POST("login")
    suspend fun login(@Body login: UsuarioLogin): Response<Usuario>
    @GET("usuario/email/{email}")
    suspend fun getUsuarioByEmail(
        @Path("email", encoded = true) email: String
    ): Response<Usuario>

    //alumno-asignaturas
    @GET("alumno/{id}/asignaturas")
    suspend fun obtenerAsignaturasAlumno(@Path("id") idAlumno: Int?): Response<MutableList<Int>>



    @GET("dios/casas")
    suspend fun getCasas(): Response<List<Casa>>

    @GET("/dios/casas/nombre/{nombre}")
    suspend fun cargaPorCasas(@Path("nombre") nombre: String):
            Response<Casa>
    @GET("/casas/{id}")
    suspend fun obtenerCasa(@Path("id") idCasa: Int): Response<Casa>

    @GET("/ranking/casas")
    suspend fun getRankingCasas(): Response<MutableList<Casa>>

    //preferencias de la casa para elegir el sombrero
    @POST("sombrero/asignar")
    suspend fun evaluarPreferencias(
        @Body prefs: PreferenciasCasa
    ): Response<CasaAsignada>

    //insertar usuario
    @POST("usuario/insertar")
    suspend fun insertarUsuario(
        @Body usuario: Usuario
    ): Response<Respuesta>   // puedes devolver mensaje
    //obtener todos los usuarios
    @GET("usuario/all")
    suspend fun getUsuarios(): Response<MutableList<Usuario>>

    //actualizar usuarios
    @PUT("usuario/actualizar")
    suspend fun actualizarUsuario(
        @Body usuario: Usuario
    ): Response<Respuesta>

    @DELETE("usuario/borrar/{email}")
    suspend fun borrarUsuario(
        @Path("email") email: String
    ): Response<Respuesta>
    //COMPROBAR ASIGNATURAS ALUMNO
    @GET("alumno/{id}/tieneAsignatura/{idAsignatura}")
    suspend fun tieneAsignatura(
        @Path("id") idAlumno: Int?,
        @Path("idAsignatura") idAsignatura: Int
    ): Response<Map<String, Boolean>>
    //HECHIZOS
    @GET("hechizos")
    suspend fun getHechizos(): Response<List<Hechizo>>

    @POST("hechizos/aprender")
    suspend fun aprenderHechizo(
        @Body body: Map<String, Int>
    ): Response<Respuesta>
    @GET("/hechizos/aprendidos/{idAlumno}")
    suspend fun getHechizosAprendidos(
        @Path("idAlumno") idAlumno: Int
    ): Response<List<Hechizo>>


    @GET("profesor/{idProfesor}/asignaturas")
    suspend fun getAsignaturasDelProfesor(@Path("idProfesor") idProfesor: Int?): Response<MutableList<Asignaturas>>

    @GET("asignaturas/{idAsignatura}/alumnos")
    suspend fun getAlumnosDeAsignatura(@Path("idAsignatura") idAsignatura: Int?): Response<MutableList<Usuario>>

    @POST("asignaturas/{idAsignatura}/alumnos/{idAlumno}")
    suspend fun addAlumnoAAsignatura(@Path("idAsignatura") idAsignatura: Int?,
                                     @Path("idAlumno") idAlumno: Int?
        ): Response<Void>

    @DELETE("asignaturas/{idAsignatura}/alumnos/{idAlumno}")
        suspend fun removeAlumnoDeAsignatura(
        @Path("idAsignatura") idAsignatura: Int?,
        @Path("idAlumno") idAlumno: Int?
        ): Response<Void>

    }
