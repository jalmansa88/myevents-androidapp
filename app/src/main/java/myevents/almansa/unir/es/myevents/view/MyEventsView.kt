package myevents.almansa.unir.es.myevents.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.myevents_view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.model.Attendee
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.EventsRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.model.User
import myevents.almansa.unir.es.myevents.utils.Constants
import myevents.almansa.unir.es.myevents.utils.toast

class MyEventsView : AppCompatActivity() {

    val TAG: String = this.javaClass.simpleName

    private val mAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private lateinit var eventAdapter: EventsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.myevents_view)

        loadEventsByUserEmail(mAuth.currentUser?.email)

    }

    private fun loadEventsByUserEmail(userEmail: String?) {

        val userRef = db.collection(Constants.USERS_TABLE_NAME).whereEqualTo(Constants.EMAIL, userEmail)

        userRef.get().addOnCompleteListener{task ->
            var user: User = task.result.documents[0].toObject(User::class.java)

            val attendeeRef = db.collection(Constants.ATTENDEES_TABLE_NAME).whereEqualTo(Constants.USER_UID, user.uid)

            val userEventAttendeeList = mutableListOf<Attendee>()

            attendeeRef.get().addOnCompleteListener{task ->

                for (doc in task.result) {
                    val attendee = doc.toObject<Attendee>(Attendee::class.java)
                    attendee.uid = doc.id
                    userEventAttendeeList.add(attendee)
                }
            }

            userEventAttendeeList.forEach{attendee -> }
        }

        val eventsRef = db.collection("events")

        eventsRef.get()
                .addOnCompleteListener{ task ->
                    val event: Event

                    if (task.isSuccessful) {
                        event = task.result.toObject(Event::class.java)
                    }
                }
    }
}
