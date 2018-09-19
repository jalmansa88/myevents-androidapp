package myevents.almansa.unir.es.myevents.presenter.interfaces

import myevents.almansa.unir.es.myevents.model.Token
import myevents.almansa.unir.es.myevents.view.interfaces.MyEventsView

interface MyEventsPresenter {

    fun setView(view: MyEventsView, token: Token)
    fun destroy()
    fun onClickLogout()

}