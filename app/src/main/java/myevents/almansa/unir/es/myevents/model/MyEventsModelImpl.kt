package myevents.almansa.unir.es.myevents.model

import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.utils.Constants
import org.jetbrains.anko.doAsyncResult
import java.util.concurrent.Future

class MyEventsModelImpl : MyEventsModel {

//    @Inject
//    lateinit var myEventsPresenter: MyEventsPresenter

    private val db = FirebaseFirestore.getInstance()
    private val mAuth = FirebaseAuth.getInstance()

    private val userRef = db.collection(Constants.USERS_TABLE_NAME)
    private val attendeeRef = db.collection(Constants.ATTENDEES_TABLE_NAME)
    private val eventsRef = db.collection(Constants.EVENTS_TABLE_NAME)

    private var currentUser = mAuth.currentUser?.email

    override fun loadEvents(): Observable<Event> {

        return Observable.create { emitter ->

            val userQuery = userRef.whereEqualTo(Constants.EMAIL, currentUser)

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

                    for (attendee in userEventAttendeeList) {

                        val eventsQuery = eventsRef.document(attendee.event_uid)

                        eventsQuery.get()
                                .addOnCompleteListener { task ->
                                    if (task.isComplete) {
                                        emitter.onNext(task.result.toObject(Event::class.java))
                                    }
                                }
                    }
                }
            }
        }
    }
}

