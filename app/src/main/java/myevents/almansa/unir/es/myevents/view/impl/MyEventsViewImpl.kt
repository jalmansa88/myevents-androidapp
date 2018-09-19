package myevents.almansa.unir.es.myevents.view.impl

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.method.TextKeyListener
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.myevents_view.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.di.Components.DaggerMyEventsComponent
import myevents.almansa.unir.es.myevents.di.Modules.MyEventsModule
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Token
import myevents.almansa.unir.es.myevents.model.adapters.EventsRecyclerViewAdapter
import myevents.almansa.unir.es.myevents.presenter.interfaces.MyEventsPresenter
import myevents.almansa.unir.es.myevents.utils.Constants
import myevents.almansa.unir.es.myevents.utils.toast
import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView
import javax.inject.Inject

class MyEventsViewImpl : AppCompatActivity(), MyEventsView {
    @Inject
    lateinit var myEventsPresenter: MyEventsPresenter

    private lateinit var eventAdapter: EventsRecyclerViewAdapter

    private val events: MutableList<Event> = mutableListOf()
    private lateinit var tokenDb: Token

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.myevents_view)
        injectDependency()

        tokenDb = getTokenFromIntent()

        myEventsPresenter.setView(this, tokenDb)

        val mLayoutManager = LinearLayoutManager(applicationContext)
        rvEvents.layoutManager = mLayoutManager
        rvEvents.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        rvEvents.itemAnimator = DefaultItemAnimator()
        eventAdapter = EventsRecyclerViewAdapter(events)
        rvEvents.adapter = eventAdapter

    }

    private fun getTokenFromIntent(): Token {
        val bundle = intent.getBundleExtra(Constants.BUNDLE)
        when (bundle == null) {
            true -> return Token()
            false -> return bundle.getParcelable(Constants.TOKEN) as Token
        }
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
        noevents_layout.visibility = View.VISIBLE
    }

    override fun showLoading() {
        rvEvents.visibility = View.INVISIBLE
        loading_layout.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        noevents_layout.visibility = View.GONE
    }

    override fun showContent() {
        rvEvents.visibility = View.VISIBLE
        loading_layout.visibility = View.INVISIBLE
        error_layout.visibility = View.GONE
        noevents_layout.visibility = View.GONE
    }

    override fun showEmpty() {
        rvEvents.visibility = View.GONE
        loading_layout.visibility = View.GONE
        error_layout.visibility = View.GONE
        noevents_layout.visibility = View.VISIBLE
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        myEventsPresenter.onClickLogout()
        return super.onOptionsItemSelected(item)
    }

    override fun navigateToLoginView() {
        val intent = Intent(this, LoginViewImpl::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}
