package myevents.almansa.unir.es.myevents.model.interfaces

import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Event

interface MyEventsModel {

    fun loadEvents(): Observable<Event>
}