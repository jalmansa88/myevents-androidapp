package myevents.almansa.unir.es.myevents.presenter.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.Token
import myevents.almansa.unir.es.myevents.model.interfaces.MyEventsModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.MyEventsPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView

class MyEventsPresenterImpl(var model: MyEventsModel) : MyEventsPresenter {

    private var view: MyEventsView? = null

    private var events = mutableListOf<Event>()
    private lateinit var token: Token

    override fun setView(view: MyEventsView, token: Token) {
        this.view = view
        this.token = token
        loadEvents(token)
    }

    private fun loadEvents(token: Token) {

        view?.showLoading()

        model.loadEvents(token)
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

    override fun onClickLogout() {
        view?.navigateToLoginView()
    }

    override fun destroy() {
        model.loadEvents(token).unsubscribeOn(Schedulers.io())
        view = null
    }

}
