package modelo

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(val nombre:String, val email:String, val contrasena:String, val foto:String,
                   val id_casa: Int, val exp:Int, val nivel:Int, val estado:String, val rol: Int)
