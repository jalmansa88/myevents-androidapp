package myevents.almansa.unir.es.myevents.model.impl

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Img
import myevents.almansa.unir.es.myevents.model.interfaces.EventImagesModel
import myevents.almansa.unir.es.myevents.utils.Constants

class EventImagesModelImpl: EventImagesModel {

    private val db = FirebaseFirestore.getInstance()

    private val imgRef = db.collection(Constants.IMAGES_TABLE_NAME)

    override fun loadImages(eventUid: String, role: Int): Observable<List<Img>> {

        val imgQuery = imgRef
                .whereEqualTo(Constants.EVENT_UID, eventUid)
                .whereEqualTo("isVip", role > 1)

        return Observable.create { emitter ->

            imgQuery.get().addOnCompleteListener {task ->

                if (task.isComplete) {
                    val images: List<Img> = task.result.toObjects<Img>(Img::class.java)
                    emitter.onNext(images)
                    emitter.onComplete()
                }

            }
        }

    }
}