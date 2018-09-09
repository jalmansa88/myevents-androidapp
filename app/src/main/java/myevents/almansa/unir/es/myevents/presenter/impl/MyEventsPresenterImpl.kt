package myevents.almansa.unir.es.myevents.presenter.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.interfaces.MyEventsModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.MyEventsPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView

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
    }

    override fun destroy() {
        model.loadEvents().unsubscribeOn(Schedulers.io())
        view = null
    }

}
