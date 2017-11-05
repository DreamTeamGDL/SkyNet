package gdl.dreamteam.skynet.Models

/**
 * Created by christopher on 21/09/17.
 */
enum class Action {
    CONNECT,
    TELL,
    HELLO,
    CONFIGURE
}

class ActionMessage(
    val Action: Int,
    val Name: String,
    val Do: String
)