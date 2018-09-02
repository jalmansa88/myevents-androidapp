package myevents.almansa.unir.es.myevents.presenter

import android.text.TextUtils
import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.model.MyEventsModel
import myevents.almansa.unir.es.myevents.model.MyEventsModelImpl
import myevents.almansa.unir.es.myevents.view.MyEventsView

class MyEventsPresenterImpl(var model: MyEventsModel) : MyEventsPresenter {

    private var view: MyEventsView? = null

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
                        view?.updateRecyclerView(it)
                    },
                    onComplete = {
                        view?.showContent()
                    },
                    onError = {
                        view?.showError()
                    }
                )
//                .subscribe({ event ->
//
//                    view?.updateRecyclerView(event)
//                },
//                {
//                    view?.showError()
//                })
    }

    override fun destroy() {
        model.loadEvents().unsubscribeOn(Schedulers.io())
        view = null
    }

}
