package myevents.almansa.unir.es.myevents.presenter.interfaces

import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView

interface MyEventsPresenter {

    fun setView(view: MyEventsView)
    fun destroy()
    fun loadEvents()
    fun onClickLogout()

}