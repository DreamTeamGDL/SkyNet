package gdl.dreamteam.skynet.Bindings

import android.databinding.ObservableField
import android.text.Editable
import android.text.TextWatcher
import gdl.dreamteam.skynet.Extensions.longToast

/**
 * Created by christopher on 18/09/17.
 */
class LoginBinding (
    username: String?,
    password: String?
) {
    constructor() : this(null, null)

    var usernameBind = ObservableField<String>(username)
    var passwordBind = ObservableField<String>(password)

    var username: String?
        get() = usernameBind.get()
        set(value) { usernameBind.set(value) }

    var password: String?
        get() = passwordBind.get()
        set(value) { passwordBind.set(value) }






}