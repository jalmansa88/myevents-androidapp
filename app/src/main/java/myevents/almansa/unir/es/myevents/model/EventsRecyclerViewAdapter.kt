package myevents.almansa.unir.es.myevents.model

import android.content.Context
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.myevents_view.view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.utils.getRolName

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
    }


    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var name: TextView = view.findViewById(R.id.tvEventName)
        internal var description: TextView = view.findViewById(R.id.tvEventDescription)
        internal var role: TextView = view.findViewById(R.id.tvEventRole)

        fun bind(event: Event) {

            name.text = event.name
            description.text = event.description
            role.text = event.getRolName()

            this.itemView.setOnClickListener{ onClickOpenEventDetail(event, this.itemView.context) }


        }
    }

}
