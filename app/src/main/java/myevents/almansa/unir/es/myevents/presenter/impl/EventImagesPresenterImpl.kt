package myevents.almansa.unir.es.myevents.presenter.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.interfaces.EventImagesModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.EventImagesPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.EventImagesView

class EventImagesPresenterImpl(var model: EventImagesModel): EventImagesPresenter {

    private var view: EventImagesView? = null

    override fun setView(view: EventImagesView, eventUid: String) {
        this.view = view
        loadImages(eventUid)
    }

    override fun destroy() {
        this.view = null
    }

    override fun loadImages(eventUrl: String) {
        view?.showLoading()

        model.loadImages(eventUrl)
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
}