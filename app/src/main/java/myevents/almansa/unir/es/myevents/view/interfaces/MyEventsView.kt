package myevents.almansa.unir.es.myevents.view.interfaces

import dagger.Component
import myevents.almansa.unir.es.myevents.model.Event

interface MyEventsView: LoadingContentView {
    fun updateRecyclerView(event: Event)
    fun navigateToLoginView()
}