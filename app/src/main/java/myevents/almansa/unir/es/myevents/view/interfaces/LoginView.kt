package myevents.almansa.unir.es.myevents.view.interfaces

import myevents.almansa.unir.es.myevents.model.Token

interface LoginView {
    fun showLoginErrorMessage()
    fun showLoginOut()
    fun showInvalidToken()
    fun navigateToEvents()
    fun navigateToEventsAnonymous(dbToken: Token)
    fun showLoading()
    fun showUiForms()
}