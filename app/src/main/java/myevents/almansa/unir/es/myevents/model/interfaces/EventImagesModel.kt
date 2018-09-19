package myevents.almansa.unir.es.myevents.model.interfaces

import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Img

interface EventImagesModel {
    fun loadImages(eventUid: String, role: Int): Observable<List<Img>>
}