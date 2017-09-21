package gdl.dreamteam.skynet.Models

/**
 * Created by christopher on 18/09/17.
 */
class LoginRequest(
    val username: String,
    val password: String,
    val grant_type: String,
    val client_id: String,
    val client_secret: String
)