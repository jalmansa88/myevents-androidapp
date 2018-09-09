package myevents.almansa.unir.es.myevents.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader {

    companion object {
        fun load(context: Context, url: String, iv: ImageView) {
            Picasso.with(context).load(url).into(iv)
        }
    }
}