package myevents.almansa.unir.es.myevents.presenter.interfaces

import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.view.interfaces.EventImagesView

interface EventImagesPresenter {
    fun setView(view: EventImagesView, event: Event)
    fun destroy()
    fun loadImages(eventUrl: String, role: Int)
}