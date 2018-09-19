package myevents.almansa.unir.es.myevents.presenter.impl

import com.facebook.AccessToken
import com.facebook.login.LoginManager
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import myevents.almansa.unir.es.myevents.model.Token
import myevents.almansa.unir.es.myevents.model.interfaces.LoginModel
import myevents.almansa.unir.es.myevents.presenter.interfaces.LoginPresenter
import myevents.almansa.unir.es.myevents.view.interfaces.LoginView

class LoginPresenterImpl(var model: LoginModel) : LoginPresenter {

    private val TAG = "LoginPresenterImpl"

    private var view: LoginView? = null

    private val facebookLoginManager = LoginManager.getInstance()
    private val mAuth = FirebaseAuth.getInstance()
    private val facebookAccessToken = AccessToken.getCurrentAccessToken()

    override fun setView(view: LoginView) {
        this.view = view
        this.view?.showUiForms()
        checkSessionAndLogout()
    }

    override fun handleFacebookAccessToken(accessToken: AccessToken) {
        val fbAuth: AuthCredential = FacebookAuthProvider.getCredential(accessToken.token)

        mAuth.signInWithCredential(fbAuth).addOnCompleteListener {
            if (!it.isSuccessful) {
                view?.showLoginErrorMessage()
            }

        }
    }

    override fun handleTemporalAccess(token: String) {
        view?.showLoading()

        var dbToken = Token()

        model.loadTemporalToken(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            dbToken = it
                        },
                        onComplete = {
                            val minutes = (System.currentTimeMillis() / 1000 - dbToken.timestamp) / 60

                            if(minutes > 60 || dbToken.value.isEmpty()) {
                                view?.showInvalidToken()
                                return@subscribeBy
                            }

                            view?.navigateToEventsAnonymous(dbToken)
                        },
                        onError = {
                            view?.showLoginErrorMessage()
                        }
                )
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

