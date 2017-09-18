package gdl.dreamteam.skynet.Others

import android.content.Context
import com.auth0.android.Auth0
import com.auth0.android.provider.WebAuthProvider
import gdl.dreamteam.skynet.R

/**
 * Created by christopher on 16/09/17.
 */

object LoginService {

    private lateinit var clientId: String
    private lateinit var url: String

    fun setup(context: Context) {
        clientId = context.getString(R.string.com_auth0_client_id)
        url = context.getString(R.string.com_auth0_domain)
    }

    fun login() {

    }

}