package myevents.almansa.unir.es.myevents.presenter.interfaces

import myevents.almansa.unir.es.myevents.view.interfaces.EventImagesView

interface EventImagesPresenter {
    fun setView(view: EventImagesView, eventUid: String, role: Int)
    fun destroy()
    fun loadImages(eventUrl: String, role: Int)
}