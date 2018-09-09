package myevents.almansa.unir.es.myevents.model.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.utils.Constants
import myevents.almansa.unir.es.myevents.utils.getRolName
import myevents.almansa.unir.es.myevents.view.impl.EventImagesViewImpl

class EventsRecyclerViewAdapter(private val eventList: MutableList<Event>)

    : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event: Event = eventList[position]

        holder.bind(event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.event_item_view, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    private fun onClickOpenEventDetail(event: Event, context: Context) {
        Toast.makeText(context, "Opened event" + event.name, Toast.LENGTH_SHORT).show()

        val intent = Intent(context, EventImagesViewImpl::class.java)
        intent.putExtra(Constants.EVENT_UID, event.uid)

        context.startActivity(intent)
    }


    inner class ViewHolder internal constructor(val view: View) : RecyclerView.ViewHolder(view) {
        internal var name: TextView = view.findViewById(R.id.tvEventName)
        internal var description: TextView = view.findViewById(R.id.tvEventDescription)
        internal var role: TextView = view.findViewById(R.id.tvEventRole)

        fun bind(event: Event) {

            name.text = event.name
            description.text = event.description
            role.text = event.getRolName()

            this.view.setOnClickListener{ onClickOpenEventDetail(event, this.itemView.context) }
        }
    }

}
