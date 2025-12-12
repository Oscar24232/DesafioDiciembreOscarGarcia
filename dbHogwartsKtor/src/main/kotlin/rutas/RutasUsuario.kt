package rutas


import AlumnoDAO
import DAO.HechizosDAO
import DAO.HechizosDAOImpl
import DAO.ProfesorDAO
import DAO.UsuarioDAO
import DAO.UsuarioDAOImpl
import ProfesorDAOImpl
import com.example.DAO.AlumnoDAOImpl
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
    val hogwartsDao: HogwartsDAO = HogwartsDAOImpl()
    val sombrero: SombreroDAO = SombreroDAOImpl()
    val usuarioDAO: UsuarioDAO = UsuarioDAOImpl()
    val alumnoAsignaturaDAO: AlumnoDAO = AlumnoDAOImpl()
    val hechizosDAO: HechizosDAO= HechizosDAOImpl()
    var profesorDAO: ProfesorDAO = ProfesorDAOImpl()

    // LOGIN
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
    //alumno ver asignaturas y hechizos
    route("/alumno") {
        get("/{id}/tieneAsignatura/{idAsignatura}") {
            val idAlumno = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, Respuesta("id inválido",1 ))
            val idAsignatura = call.parameters["idAsignatura"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, Respuesta("id asignatura inválido",1 ))

            val tiene = alumnoAsignaturaDAO.alumnoTieneAsignatura(idAlumno, idAsignatura)
            call.respond(HttpStatusCode.OK, mapOf("tiene" to tiene))
        }

        get("/{id}/asignaturas") {
            val idAlumno = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, Respuesta("ID inválido",1 ))
            val asignaturas = alumnoAsignaturaDAO.obtenerAsignaturasDeAlumno(idAlumno)
            call.respond(HttpStatusCode.OK, asignaturas)
        }
    }

    route("/hechizos") {
        get {
            val lista = hechizosDAO.getHechizos()
            call.respond(HttpStatusCode.OK, lista)
        }

        post("/aprender") {
            val body = call.receive<Map<String, Int>>() // { "id_hechizo":1, "id_alumno":2 }
            val idHechizo = body["id_hechizo"] ?: return@post call.respond(HttpStatusCode.BadRequest, Respuesta("id_hechizo falta",1))
            val idAlumno = body["id_alumno"] ?: return@post call.respond(HttpStatusCode.BadRequest, Respuesta("id_alumno falta",1))

            val ok = hechizosDAO.aprenderHechizo(idHechizo, idAlumno)
            if (ok) call.respond(HttpStatusCode.OK, Respuesta("Hechizo aprendido",0))
            else call.respond(HttpStatusCode.Conflict, Respuesta("No se pudo aprender",1))
        }
        //para la recycler de los hechizos aprendidos
        get("/aprendidos/{idAlumno}") {
            val idAlumno = call.parameters["idAlumno"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, Respuesta("ID inválido", 1))

            val lista = hechizosDAO.getHechizosAprendidos(idAlumno)
            call.respond(HttpStatusCode.OK, lista)
        }


    }


    //LISTADO
    route("/sombrero") {

        post("/asignar") {
            // Recibir preferencias desde Android
            val prefs = call.receive<PreferenciasCasa>()
            val preferencias = PreferenciasCasa(prefs.preferencias)

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
        val bool = usuarioDAO.insertar(usuario)

        if (bool) {
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
    get("/usuario/email/{email}") {

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
    //TODOS LOS USUARIOS
    get("/usuario/all") {
        val usuarios = usuarioDAO.obtenerTodosAlumnos()

        if (usuarios.isEmpty()) {
            call.respond(HttpStatusCode.NotFound, "No hay usuarios en la base de datos")
        } else {
            call.respond(HttpStatusCode.OK, usuarios)
        }
    }
    put("/usuario/actualizar") {

        val usuario = call.receive<Usuario>()

        val bool = usuarioDAO.actualizarUsuario(usuario)

        if (bool) {
            call.respond(
                HttpStatusCode.OK,
                Respuesta("Usuario actualizado correctamente", 0)
            )
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                Respuesta("No existe el usuario con email=${usuario.email}", 1)
            )
        }
    }


    // BORRAR POR EMAIL

    delete("/usuario/borrar/{email}") {

        val email = call.parameters["email"]

        if (email.isNullOrBlank()) {
            call.respond(HttpStatusCode.BadRequest, "Falta email")
            return@delete
        }

        val bool = usuarioDAO.borrarUsuario(email)

        if (bool) {
            call.respond(
                HttpStatusCode.OK,
                Respuesta("Usuario eliminado correctamente", 0)
            )
        } else {
            call.respond(
                HttpStatusCode.NotFound,
                Respuesta("No existe el usuario con email=$email", 1)
            )
        }
    }
    //ver casas
    route("/listado") {
        get {
            val personas = hogwartsDao.obtenerTodos()
            return@get call.respond(HttpStatusCode.OK, personas)
        }
        // Buscar casa por nombre no tocar
        get("/casas/{nombre}") {

            val nombre = call.parameters["nombre"]
                ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "El nombre de la casa no puede estar vacío"
                )

            val casa = hogwartsDao.obtenerPorNombre(nombre)
                ?: return@get call.respond(
                    HttpStatusCode.NotFound,
                    "No existe ninguna casa con el nombre: $nombre"
                )

            call.respond(HttpStatusCode.OK, casa)
        }
    }
    // /listado/casas/1
    get("/casas/{id}") {

        val id = call.parameters["id"]?.toIntOrNull()
            ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "ID inválido"
            )

        val casa = hogwartsDao.obtenerPorId(id)
            ?: return@get call.respond(
                HttpStatusCode.NotFound,
                "No existe una casa con id=$id"
            )

        call.respond(HttpStatusCode.OK, casa)
    }
    get("/ranking/casas") {
        try {
            val ranking = hogwartsDao.getRankingCasas()
            println("Ranking generado con ${ranking.size} casas")
            call.respond(ranking)
        } catch (e: Exception) {
            println("ERROR RANKING CASAS: ${e.message}")
            call.respond(HttpStatusCode.InternalServerError, "Error: ${e.message}")
        }
    }
    get("/profesor/{idProfesor}/asignaturas") {
        val idProfesor = call.parameters["idProfesor"]?.toIntOrNull()

        if (idProfesor == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@get
        }

        val asignaturas = profesorDAO.getAsignaturasDelProfesor(idProfesor)

        call.respond(HttpStatusCode.OK, asignaturas)
    }
    get("/asignaturas/{idAsignatura}/alumnos") {
        val idAsignatura = call.parameters["idAsignatura"]?.toIntOrNull()

        if (idAsignatura == null) {
            call.respond(HttpStatusCode.BadRequest, "ID inválido")
            return@get
        }

        val alumnos = profesorDAO.getAlumnosDeAsignatura(idAsignatura)

        call.respond(HttpStatusCode.OK, alumnos)
    }
    post("/asignaturas/{idAsignatura}/alumnos/{idAlumno}") {
        val idAsignatura = call.parameters["idAsignatura"]?.toIntOrNull()
        val idAlumno = call.parameters["idAlumno"]?.toIntOrNull()

        if (idAsignatura == null || idAlumno == null) {
            call.respond(HttpStatusCode.BadRequest, "Datos inválidos")
            return@post
        }

        val yaExiste = profesorDAO.existeRelacionAlumnoAsignatura(idAlumno, idAsignatura)

        if (yaExiste) {
            call.respond(HttpStatusCode.Conflict, "El alumno ya está en la asignatura")
            return@post
        }

        profesorDAO.insertAlumnoEnAsignatura(idAlumno, idAsignatura)

        call.respond(HttpStatusCode.OK)
    }
    delete("/asignaturas/{idAsignatura}/alumnos/{idAlumno}") {
        val idAsignatura = call.parameters["idAsignatura"]?.toIntOrNull()
        val idAlumno = call.parameters["idAlumno"]?.toIntOrNull()

        if (idAsignatura == null || idAlumno == null) {
            call.respond(HttpStatusCode.BadRequest, "Datos inválidos")
            return@delete
        }

        profesorDAO.eliminarAlumnoDeAsignatura(idAlumno, idAsignatura)

        call.respond(HttpStatusCode.OK)
    }


}

