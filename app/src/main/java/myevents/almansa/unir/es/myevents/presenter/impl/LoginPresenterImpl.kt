package myevents.almansa.unir.es.myevents.presenter.impl

import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import myevents.almansa.unir.es.myevents.presenter.interfaces.LoginPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.LoginView

class LoginPresenterImpl : LoginPresenter {

    private val TAG = "LoginPresenterImpl"

    private var view: LoginView? = null

    private val facebookLoginManager = LoginManager.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val facebookAccessToken = AccessToken.getCurrentAccessToken()

    override fun setView(view: LoginView) {
        this.view = view
        checkSessionAndLogout()
    }

    override fun handleFacebookAccesToken(accessToken: AccessToken) {
        val fbAuth: AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)

        mAuth.signInWithCredential(fbAuth).addOnCompleteListener {
            if (!it.isSuccessful) {
                view?.showLoginErrorMessage()
            }

        }
    }

    override fun destroy() {
        this.view = null
    }

    private fun checkSessionAndLogout() {

        if (mAuth.currentUser != null) {
            view?.showLoginOut()
            mAuth.signOut()
        }

        if (facebookAccessToken != null) {
            facebookLoginManager.logOut()
        }
    }
}

