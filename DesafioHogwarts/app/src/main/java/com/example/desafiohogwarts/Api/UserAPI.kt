package Api

import com.example.desafiohogwarts.modelo.Casa
import com.example.desafiohogwarts.modelo.CasaAsignada
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
    @GET("usuarios/email/{email}")
    suspend fun getUsuarioByEmail(
        @Path("email", encoded = true) email: String
    ): Response<Usuario>



    @GET("dios/casas")
    suspend fun getCasas(): Response<List<Casa>>

    @GET("/dios/casas/nombre/{nombre}")
    suspend fun loginRespuesta(@Path("nombre") nombre: String):
            Response<Casa>
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