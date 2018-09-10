package myevents.almansa.unir.es.myevents.presenter.impl

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.Img
import myevents.almansa.unir.es.myevents.model.interfaces.EventImagesModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.EventImagesPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.EventImagesView
import java.util.*

class EventImagesPresenterImpl(var model: EventImagesModel): EventImagesPresenter {

    private var view: EventImagesView? = null

    private lateinit var images: List<Img>

    override fun setView(view: EventImagesView, eventUid: String, role: Int) {
        this.view = view
        loadImages(eventUid, role)
    }

    override fun destroy() {
        this.view = null
    }

    override fun loadImages(eventUrl: String, role: Int) {
        view?.showLoading()

        model.loadImages(eventUrl, role)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            images = it
                            view?.updateRecyclerView(it)
                        },
                        onComplete = {
                           if (images.isEmpty()) view?.showEmpty() else view?.showContent()
                            images = emptyList()
                        },
                        onError = {
                            view?.showError()
                        }
                )
    }
}