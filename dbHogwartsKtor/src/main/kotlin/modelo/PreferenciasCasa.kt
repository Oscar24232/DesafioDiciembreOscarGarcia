package modelo

@kotlinx.serialization.Serializable
data class PreferenciasCasa(
    val preferencias: HashMap<String, Int>
)