package myevents.almansa.unir.es.myevents.view.interfaces

interface LoginView: ShowToast {
    fun showLoginEmailForm()
    fun hideLoginEmailForm()
    fun showFacebookLoginButton()
    fun hideFacebookLoginButton()
    fun showLoginErrorMessage()
    fun showLoginOut()
}