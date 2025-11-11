package Api

import com.example.desafiohogwarts.modelo.Casa
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
    data class Respuesta(val mensaje: String, val codigo: Int)
    @GET("listado")
    suspend fun getCasas(): Response<List<Casa>>
    @GET("listado/{nombre}")
    suspend fun getCasaporNombre(@Path("nombre") nombre: String): Response<Casa>
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