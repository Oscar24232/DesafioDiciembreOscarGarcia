package rutas


import DAO.UsuarioDAO
import DAO.UsuarioDAOImpl
import com.example.DAO.HogwartsDAO
import com.example.DAO.HogwartsDAOImpl
import com.example.DAO.SombreroDAO
import com.example.DAO.SombreroDAOImpl
import com.example.modelo.UsuarioLogin
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.PreferenciasCasa
import modelo.Respuesta
import modelo.Usuario


fun Route.misRutas() {
    val personaDAO: UsuarioDAO = UsuarioDAOImpl()
    val hogwartsDao: HogwartsDAO = HogwartsDAOImpl()
    val sombrero:SombreroDAO = SombreroDAOImpl()
    val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()

    // 🔹 LISTADO
    route("/sombrero") {

        post("/asignar") {
            // Recibir preferencias desde Android
            val prefs = call.receive<PreferenciasCasa>()
            val preferencias= PreferenciasCasa( prefs.preferencias)

            // Ejecutar la lógica del sombrero seleccionador
            val idCasaAsignada = sombrero.asignarCasa(preferencias)

            // Respuesta al cliente Android
            call.respond(
                HttpStatusCode.OK,
                idCasaAsignada
            )
            println("idCasa: ${idCasaAsignada}")

            //terminar sombrero
                //ver si devuelve el log
                // y terminar con el insert en la bbdd

        }
    }
    post("/usuario/insertar") {

        val usuario = call.receive<Usuario>()
        print(usuario.nombre)
        // Validar email repetido
        if (usuarioDAO.existeEmail(usuario.email)) {
            return@post call.respond(
                HttpStatusCode.Conflict,
                Respuesta("Este correo ya esta registrado", 1)
            )
        }
        // Insertar usuario
        val ok = usuarioDAO.insertar(usuario)

        if (ok) {
            call.respond(
                HttpStatusCode.Created,
                Respuesta("Usuario insertado correctamente", 0)
            )
        } else {
            call.respond(
                HttpStatusCode.InternalServerError,
                Respuesta("Error insertado ", 2)
            )
        }
    }
    get("/usuarios/email/{email}") {

        val email = call.parameters["email"]

        if (email.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Falta email")
            return@get
        }

        val usuario = usuarioDAO.getUsuarioByEmail(email)

        if (usuario != null) {
            call.respond(HttpStatusCode.OK, usuario)
        } else {
            call.respond(HttpStatusCode.NotFound, "Usuario no encontrado")
        }
    }





    //ver casas
    route("/listado") {
        get {
            val personas = hogwartsDao.obtenerTodos()
            return@get call.respond(HttpStatusCode.OK, personas)
        }
        //casas po nombre
        get("{nombre?}") {
            val nombre = call.parameters["nombre"]
                ?: return@get call.respondText("nombre vacío en la URL", status = HttpStatusCode.BadRequest)

            val casa = hogwartsDao.obtener(nombre = nombre)
                ?: return@get call.respond(HttpStatusCode.NotFound, Respuesta("No existe ninguna casa con ese nombre=$nombre", 1))

            return@get call.respond(HttpStatusCode.OK, casa)
        }
    }

    // 🔹 LOGIN
    route("/login") {
        post {
            val datos = call.receive<UsuarioLogin>()
            val usuario = usuarioDAO.login(datos.email, datos.contrasena)

            if (usuario == null) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    Respuesta("Email o contraseña incorrectos", 1)
                )
            } else {
                call.respond(HttpStatusCode.OK, usuario)
            }
        }
    }



    // 🔹 BORRAR
    route("/borrar") {
        delete("{dni?}") {
            val dni = call.parameters["dni"]
                ?: return@delete call.respondText("DNI vacío en la URL", status = HttpStatusCode.BadRequest)

            val eliminado = personaDAO.eliminar(dni)
            if (eliminado) {
                return@delete call.respond(HttpStatusCode.OK, Respuesta("Persona con DNI $dni eliminada correctamente", 0))
            } else {
                return@delete call.respond(HttpStatusCode.NotFound, Respuesta("No existe ninguna persona con DNI $dni", 1))
            }
        }
    }

}

