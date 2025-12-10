package Api

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
    @GET("hechizos/{id}/hechizosAprendidos")
    suspend fun getHechizosAprendidos(
        @Path("id") idAlumno: Int
    ): Response<List<Int>>












    /*
        @POST("login")
        suspend fun login(@Body cred: UsuarioLogin): Response<Respuesta>

        @GET("listado/{dni}")
        suspend fun getUsuarioPorDni(@Path("dni") dni: String): Response<Usuario>

        @GET("encuestas")
        suspend fun getEncuestas(): Response<List<Encuesta>>

        @GET("encuestas/{id}")
        suspend fun getEncuesta(@Path("id") id: Int): Response<Encuesta>

        @POST("encuestas/registrar")
        suspend fun addEncuesta(@Body encuestaData: Encuesta): Response<Respuesta>

        @DELETE("encuestas/borrar/{id}")
        suspend fun delEncuesta(@Path("id") id: Int): Response<Boolean>

        @PUT("encuestas/modificar/{id}")
        suspend fun updateEncuesta(
            @Path("id") id: Int,
            @Body encuestaData: Encuesta
        ): Response<Boolean>

     */
}