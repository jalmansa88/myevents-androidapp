package myevents.almansa.unir.es.myevents.view

import dagger.Component
import myevents.almansa.unir.es.myevents.model.Event

interface MyEventsView {
    fun updateRecyclerView(event: Event)
    fun showToast(msg: String)
    fun showContent()
    fun showError()
    fun showLoading()
}