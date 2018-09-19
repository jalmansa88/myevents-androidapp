package myevents.almansa.unir.es.myevents.presenter.interfaces

import com.facebook.AccessToken
import myevents.almansa.unir.es.myevents.view.interfaces.LoginView

interface LoginPresenter {
    fun setView(view: LoginView)
    fun destroy()
    fun handleFacebookAccessToken(accessToken: AccessToken)
    fun handleTemporalAccess(token: String)
}