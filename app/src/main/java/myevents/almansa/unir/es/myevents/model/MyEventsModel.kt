package myevents.almansa.unir.es.myevents.model

import io.reactivex.Observable

interface MyEventsModel {

    fun loadEvents(): Observable<Event>
}