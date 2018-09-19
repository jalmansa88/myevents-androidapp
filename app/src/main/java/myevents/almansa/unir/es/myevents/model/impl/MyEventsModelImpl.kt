package myevents.almansa.unir.es.myevents.model.impl

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Attendee
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Token
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

    override fun loadEvents(token: Token): Observable<Event> {

        if (token.value.isEmpty()) return getEvents()

        return getEventsWithAnonymousToken(token)
    }

    private fun getEventsWithAnonymousToken(token: Token): Observable<Event> {
        return Observable.create { emitter ->
            eventsRef
                .document(token.eventId)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val event = task.result.toObject(Event::class.java)
                        event.uid = task.result.id
                        emitter.onNext(event)
                        emitter.onComplete()
                    }
                }

        }
    }

    private fun getEvents(): Observable<Event> {
        return Observable.create { emitter ->

            val userQuery = userRef.whereEqualTo(Constants.EMAIL, currentUserEmail)

            userQuery.get().addOnCompleteListener { task ->
                val doc = task.result.documents[0]
                var user: User = doc.toObject(User::class.java)
                user.uid = doc.id

                val attendeeQuery = attendeeRef.whereEqualTo(Constants.USER_UID, user.uid)
                var userEventAttendeeList: MutableList<Attendee>

                attendeeQuery.get().addOnCompleteListener {

                    userEventAttendeeList = toAttendeeList(it)

                    if (userEventAttendeeList.size == 0) {
                        emitter.onComplete()
                    }

                    var counter = AtomicInteger(0)
                    for (attendee in userEventAttendeeList) {

                        val eventsQuery = eventsRef.document(attendee.event_uid)

                        eventsQuery.get()
                                .addOnCompleteListener {
                                    if (it.isComplete) {
                                        counter.incrementAndGet()

                                        emitter.onNext(toEventMapper(it.result, attendee))

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

    private fun toAttendeeList(task: Task<QuerySnapshot>): MutableList<Attendee> {
        return task.result
                .map(this::newAttendeeFromDoc)
                .toMutableList()

//        for (doc in task.result) {
//            val attendee = toAttendee(doc)
//            list.add(attendee)
//        }
    }

    private fun newAttendeeFromDoc(doc: DocumentSnapshot): Attendee {
        val attendee = doc.toObject<Attendee>(Attendee::class.java)
        attendee.uid = doc.id
        return attendee
    }


    private fun toEventMapper(doc: DocumentSnapshot, attendee: Attendee): Event {
        val event = doc.toObject(Event::class.java)
        event.uid = doc.id
        event.role = attendee.role
        return event
    }
}

