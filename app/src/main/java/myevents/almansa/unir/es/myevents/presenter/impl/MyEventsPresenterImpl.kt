package myevents.almansa.unir.es.myevents.presenter.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Img
import myevents.almansa.unir.es.myevents.model.interfaces.MyEventsModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.MyEventsPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView
import java.util.*

class MyEventsPresenterImpl(var model: MyEventsModel) : MyEventsPresenter {

    private var view: MyEventsView? = null

    private var events = mutableListOf<Event>()

    override fun setView(view: MyEventsView) {
        this.view = view
        loadEvents()
    }

    override fun loadEvents() {

        view?.showLoading()

        model.loadEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        events.add(it)
                        view?.updateRecyclerView(it)
                    },
                    onComplete = {
                        if (events.isEmpty()) view?.showEmpty() else view?.showContent()
                        events.clear()
                    },
                    onError = {
                        view?.showError()
                    }
                )
    }

    override fun destroy() {
        model.loadEvents().unsubscribeOn(Schedulers.io())
        view = null
    }

}
