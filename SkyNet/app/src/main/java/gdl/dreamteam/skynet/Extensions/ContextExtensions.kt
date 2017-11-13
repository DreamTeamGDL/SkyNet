package gdl.dreamteam.skynet.Extensions

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import gdl.dreamteam.skynet.R

/**
 * Created by christopher on 19/09/17.
 */

fun Context.shortToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.longToast(message: CharSequence) =
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()

fun Context.bork() {
    val mediaPlayer = MediaPlayer.create(this, R.raw.bork)
    mediaPlayer.start()
}