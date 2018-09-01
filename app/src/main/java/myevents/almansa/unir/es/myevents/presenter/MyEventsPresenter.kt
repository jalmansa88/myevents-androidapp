package myevents.almansa.unir.es.myevents.presenter

import dagger.Component
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.view.MyEventsView

interface MyEventsPresenter {

    fun setView(view: MyEventsView)
    fun destroy()
    fun loadEvents()
}