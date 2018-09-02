package myevents.almansa.unir.es.myevents.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.myevents_view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.di.Components.DaggerMyEventsComponent
import myevents.almansa.unir.es.myevents.di.Modules.MyEventsModule
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.EventsRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenter
import myevents.almansa.unir.es.myevents.utils.toast
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
        rvEvents.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvEvents.itemAnimator = DefaultItemAnimator()
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

    override fun showError() {
        rvEvents.visibility = View.INVISIBLE
        loading_layout.visibility = View.INVISIBLE
        error_layout.visibility = View.VISIBLE
    }

    override fun showLoading() {
        rvEvents.visibility = View.INVISIBLE
        loading_layout.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
    }

    override fun showContent() {
        rvEvents.visibility = View.VISIBLE
        loading_layout.visibility = View.INVISIBLE
        error_layout.visibility = View.GONE
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

}
