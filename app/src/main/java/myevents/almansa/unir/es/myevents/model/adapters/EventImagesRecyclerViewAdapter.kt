package myevents.almansa.unir.es.myevents.model.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.model.Img
import myevents.almansa.unir.es.myevents.utils.ImageLoader

class EventImagesRecyclerViewAdapter(private val images: List<Img>, private val isUserVip: Boolean): RecyclerView.Adapter<EventImagesRecyclerViewAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val img: Img = images[position]

        holder.bind(img, isUserVip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.image_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class ViewHolder internal constructor(view: View): RecyclerView.ViewHolder(view) {
        internal var pic: ImageView = view.findViewById(R.id.ivImageItem)
        internal var title: TextView = view.findViewById(R.id.tvImageItemTitle)
        internal var lock: ImageView = view.findViewById(R.id.ivImagesLock)

        fun bind(img: Img, isUserVip: Boolean) {
            ImageLoader.load(this.itemView.context, img.url, pic)
            title.text = img.nombre
            if (isUserVip && img.isVip) lock.visibility = View.VISIBLE
        }
    }
}