package myevents.almansa.unir.es.myevents.model

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.google.firebase.firestore.FirebaseFirestore
import myevents.almansa.unir.es.myevents.R

class EventsRecyclerViewAdapter(private val eventList: MutableList<Event>,
                                private val context: Context,
                                private val db: FirebaseFirestore)
    : RecyclerView.Adapter<EventsRecyclerViewAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = eventList[position]

        holder.name.setText(event.name)
        holder.description.setText(event.description)

//        holder.edit.setOnClickListener { updateNote(note) }
//        holder.delete.setOnClickListener { deleteNote(note.id!!, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    }

    override fun getItemCount(): Int {
        return eventList.size
    }


    inner class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {

        internal var name: EditText = view.findViewById(R.id.etEventName)
        internal var description: EditText = view.findViewById(R.id.etEventDescription)

//            edit = view.findViewById(R.id.ivEdit)
//            delete = view.findViewById(R.id.ivDelete)
    }

}
