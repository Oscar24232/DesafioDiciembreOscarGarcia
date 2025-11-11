package rutas


import DAO.PersonaDAO
import DAO.PersonaDAOImpl
import com.example.DAO.EncuestaDAOImpl
import com.example.DAO.HogwartsDAO
import com.example.DAO.HogwartsDAOImpl
import com.example.modelo.Encuesta
import com.example.modelo.Persona
import com.example.modelo.UsuarioLogin
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import modelo.Respuesta
import modelo.Usuario


fun Route.misRutas() {
    val personaDAO: PersonaDAO = PersonaDAOImpl()
    val hogwartsDao: HogwartsDAO = HogwartsDAOImpl()

    // 🔹 LISTADO
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
            val us = call.receive<UsuarioLogin>()
            println("📩 Login recibido: dni=${us.dni}, clave=${us.clave}") // 👈 imprime lo que recibe
            val persona = personaDAO.obtener(us.dni)

            if (persona == null || persona.clave != us.clave) {
                return@post call.respond(
                    HttpStatusCode.Unauthorized,
                    Respuesta("DNI o clave incorrectos", 1)
                )
            }

            return@post call.respond(HttpStatusCode.OK, Respuesta("Inicio de sesión exitoso", 0))
        }
    }

    // 🔹 REGISTRAR
    route("/registrar") {
        post {
            val us = call.receive<Usuario>()

            val existente = personaDAO.obtener(us.dni)
            if (existente != null) {
                return@post call.respond(
                    HttpStatusCode.Conflict,
                    Respuesta("Ya existe una persona con ese DNI", 1)
                )
            }

            val insertado = personaDAO.insertar(
                Persona(
                    dni = us.dni,
                    nombre = us.nombre,
                    clave = us.clave,
                    tfno = us.tfno,
                    rol = us.rol
                )
            )

            if (insertado) {
                return@post call.respond(HttpStatusCode.Created, Respuesta("Usuario registrado correctamente", 0))
            } else {
                return@post call.respond(HttpStatusCode.InternalServerError, Respuesta("Error al registrar usuario", 2))
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

    // 🔹 MODIFICAR
    route("/modificar") {
        put("{dni?}") {
            val dni = call.parameters["dni"]
                ?: return@put call.respondText("DNI vacío en la URL", status = HttpStatusCode.BadRequest)
            val us = call.receive<Usuario>()

            val existente = personaDAO.obtener(dni)
            if (existente == null) {
                return@put call.respond(HttpStatusCode.NotFound, Respuesta("No existe ninguna persona con DNI $dni", 1))
            }

            val actualizado = personaDAO.actualizar(
                Persona(
                    dni = dni,
                    nombre = us.nombre,
                    clave = us.clave,
                    tfno = us.tfno,
                    rol = us.rol
                )
            )

            if (actualizado) {
                return@put call.respond(HttpStatusCode.OK, Respuesta("Persona modificada correctamente", 0))
            } else {
                return@put call.respond(HttpStatusCode.InternalServerError, Respuesta("Error al modificar persona", 2))
            }
        }
    }
    //encuestas
    val encuestaDAO = EncuestaDAOImpl()

    route("/encuestas") {
        get {
            val lista = encuestaDAO.obtenerTodas()
            call.respond(lista)
        }
    }
    // 🔹 INSERTAR ENCUESTA
    route("/encuestas/registrar") {
        post {
            val nuevaEncuesta = call.receive<Encuesta>()
            println("🟢 Insertando encuesta: $nuevaEncuesta")

            val ok = encuestaDAO.insertar(nuevaEncuesta)
            if (ok) {
                call.respond(HttpStatusCode.OK, Respuesta("Encuesta insertada correctamente", 0))
            } else {
                call.respond(HttpStatusCode.InternalServerError, Respuesta("Error al insertar encuesta", 1))
            }
        }
    }


}

