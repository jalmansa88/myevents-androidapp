package myevents.almansa.unir.es.myevents.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.myevents_view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.di.Components.DaggerAppComponent
import myevents.almansa.unir.es.myevents.di.Components.DaggerMyEventsComponent
import myevents.almansa.unir.es.myevents.di.Modules.MyEventsModule
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.EventsRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenter
import myevents.almansa.unir.es.myevents.utils.toast
import org.jetbrains.anko.toast
import javax.inject.Inject

class MyEventsViewImpl : AppCompatActivity(), MyEventsView {
    @Inject
    lateinit var myEventsPresenter: MyEventsPresenter

    private lateinit var eventAdapter: EventsRecyclerViewAdapter

    private val events: MutableList<Event> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.myevents_view)
        injectDependency()

        myEventsPresenter.setView(this)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvEvents.layoutManager = mLayoutManager
//        rvEvents.itemAnimator = DefaultItemAnimator()
        eventAdapter = EventsRecyclerViewAdapter(events)
        rvEvents.adapter = eventAdapter

    }

    override fun updateRecyclerView(event: Event) {
        events.add(event)
        eventAdapter = EventsRecyclerViewAdapter(events)
        rvEvents.adapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        myEventsPresenter.destroy()
    }

    private fun injectDependency() {
        val activityComponent = DaggerMyEventsComponent.builder()
                .myEventsModule(MyEventsModule(this))
                .build()

        activityComponent.inject(this)
    }

    override fun showError(msg: String) {
        toast(msg)
    }

    override fun showSuccess(msg: String) {
        toast(msg)
    }
}
