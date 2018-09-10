package myevents.almansa.unir.es.myevents.view.impl

import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.event_images_view.*
import kotlinx.android.synthetic.main.myevents_view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.di.Components.DaggerEventImagesComponent
import myevents.almansa.unir.es.myevents.di.Modules.EventImagesModule
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Img
import myevents.almansa.unir.es.myevents.model.adapters.EventImagesRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.model.adapters.EventsRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.presenter.interfaces.EventImagesPresenter
import myevents.almansa.unir.es.myevents.utils.Constants
import myevents.almansa.unir.es.myevents.view.interfaces.EventImagesView
import myevents.almansa.unir.es.myevents.view.interfaces.LoadingContentView
import javax.inject.Inject

class EventImagesViewImpl : AppCompatActivity(), EventImagesView {
    @Inject
    lateinit var eventImagesPresenter: EventImagesPresenter

    private lateinit var eventImagesAdapter: EventImagesRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.event_images_view)
        injectDependency()

        val event = intent.extras.get(Constants.EVENT) as Event

        eventImagesPresenter.setView(this, event.uid, event.role)

        tvEventEventname.text = event.name

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvEventImages.layoutManager = mLayoutManager
        rvEventImages.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvEventImages.itemAnimator = DefaultItemAnimator()

        eventImagesAdapter = EventImagesRecyclerViewAdapter(mutableListOf())
        rvEventImages.adapter = eventImagesAdapter
    }

    override fun updateRecyclerView(images: List<Img>) {
        eventImagesAdapter = EventImagesRecyclerViewAdapter(images)
        rvEventImages.adapter = eventImagesAdapter
    }


    override fun showToast(msg: String) {
    }

    override fun showError() {
        eventImages_content.visibility = View.INVISIBLE
        eventImages_loading.visibility = View.INVISIBLE
        eventImages_error.visibility = View.VISIBLE
        eventImages_empty.visibility = View.GONE
    }

    override fun showLoading() {
        eventImages_content.visibility = View.INVISIBLE
        eventImages_loading.visibility = View.VISIBLE
        eventImages_error.visibility = View.GONE
        eventImages_empty.visibility = View.GONE
    }

    override fun showContent() {
        eventImages_content.visibility = View.VISIBLE
        eventImages_loading.visibility = View.INVISIBLE
        eventImages_error.visibility = View.GONE
        eventImages_empty.visibility = View.GONE
    }

    override fun showEmpty() {
        eventImages_content.visibility = View.GONE
        eventImages_loading.visibility = View.GONE
        eventImages_error.visibility = View.GONE
        eventImages_empty.visibility = View.VISIBLE
    }

    private fun injectDependency() {
        val activityComponent = DaggerEventImagesComponent.builder()
                .eventImagesModule(EventImagesModule(this))
                .build()

        activityComponent.inject(this)
    }
}
