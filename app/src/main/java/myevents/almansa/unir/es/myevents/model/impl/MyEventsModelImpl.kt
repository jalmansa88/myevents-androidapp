package myevents.almansa.unir.es.myevents.model.impl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Attendee
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.interfaces.MyEventsModel
import myevents.almansa.unir.es.myevents.model.User
import myevents.almansa.unir.es.myevents.utils.Constants
import java.util.concurrent.atomic.AtomicInteger

class MyEventsModelImpl : MyEventsModel {

    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

    private val userRef = db.collection(Constants.USERS_TABLE_NAME)
    private val attendeeRef = db.collection(Constants.ATTENDEES_TABLE_NAME)
    private val eventsRef = db.collection(Constants.EVENTS_TABLE_NAME)

    private var currentUserEmail = mAuth.currentUser?.email

    override fun loadEvents(): Observable<Event> {

        return Observable.create { emitter ->

            val userQuery = userRef.whereEqualTo(Constants.EMAIL, currentUserEmail)

            userQuery.get().addOnCompleteListener { task ->
                val doc = task.result.documents[0]
                var user: User = doc.toObject(User::class.java)
                user.uid = doc.id

                val attendeeQuery = attendeeRef.whereEqualTo(Constants.USER_UID, user.uid)

                val userEventAttendeeList = mutableListOf<Attendee>()

                attendeeQuery.get().addOnCompleteListener { task ->

                    for (doc in task.result) {
                        val attendee = doc.toObject<Attendee>(Attendee::class.java)
                        attendee.uid = doc.id
                        userEventAttendeeList.add(attendee)
                    }

                    if (userEventAttendeeList.size == 0){
                        emitter.onComplete()
                    }

                    var counter = AtomicInteger(0)
                    for (attendee in userEventAttendeeList) {

                        val eventsQuery = eventsRef.document(attendee.event_uid)

                        eventsQuery.get()
                                .addOnCompleteListener { task ->
                                    if (task.isComplete) {
                                        counter.incrementAndGet()

                                        val doc = task.result
                                        val event = doc.toObject(Event::class.java)
                                        event.uid = doc.id
                                        event.role = attendee.role
                                        emitter.onNext(event)

                                        if (counter.get() == userEventAttendeeList.size) {
                                            emitter.onComplete()
                                        }
                                    }
                                }
                    }
                }
            }
        }
    }
}

