package gdl.dreamteam.skynet.Models

/**
 * Created by christopher on 28/08/17.
 */
class Zone (
    var name: String,
    var clients: Array<Client>
) {
    override fun equals(other: Any?): Boolean {
        val that = other as Zone
        return this.name == that.name
    }
}