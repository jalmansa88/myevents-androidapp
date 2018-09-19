package myevents.almansa.unir.es.myevents.model.interfaces

import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Token

interface MyEventsModel {

    fun loadEvents(token: Token): Observable<Event>
}