package myevents.almansa.unir.es.myevents.utils

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Picasso
import myevents.almansa.unir.es.myevents.R

class ImageLoader {

    companion object {
        fun load(context: Context, url: String, iv: ImageView) {
            Picasso
                    .with(context)
                    .load(url)
                    .placeholder(R.drawable.ic_menu_gallery)
                    .error(R.drawable.ic_broken_image)
                    .into(iv)
        }
    }
}