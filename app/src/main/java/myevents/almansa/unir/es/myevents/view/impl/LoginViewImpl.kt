package myevents.almansa.unir.es.myevents.view.impl

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import com.facebook.CallbackManager
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.R.string.*
import myevents.almansa.unir.es.myevents.di.Components.DaggerLoginComponent
import myevents.almansa.unir.es.myevents.di.Modules.LoginModule
import myevents.almansa.unir.es.myevents.presenter.interfaces.LoginPresenter
import myevents.almansa.unir.es.myevents.utils.registerCallback
import myevents.almansa.unir.es.myevents.utils.toast
import myevents.almansa.unir.es.myevents.view.interfaces.LoginView
import org.jetbrains.anko.toast
import java.util.*
import javax.inject.Inject

class LoginViewImpl : AppCompatActivity(), LoginView {

    private val TAG = "LoginViewImpl"

    @Inject
    lateinit var loginPresenter: LoginPresenter

    private val callbackManager = CallbackManager.Factory.create()

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
        setContentView(R.layout.activity_login)
        injectDependency()

        loginPresenter.setView(this)

        configureButtons()
    }

    override fun showLoginErrorMessage() {
        toast(getString(fb_login_error))
    }

    override fun showLoginEmailForm() {
        login_form.visibility = View.VISIBLE
    }

    override fun hideLoginEmailForm() {
        login_form.visibility = View.INVISIBLE
    }

    override fun showLoginOut() {
        toast(getString(login_out))
    }

    override fun showFacebookLoginButton() {
        facebook_login.visibility = View.VISIBLE
    }

    override fun hideFacebookLoginButton() {
        facebook_login.visibility = View.INVISIBLE
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener(firebaseAuthListener)
    }


    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(firebaseAuthListener)
    }

    private fun configureButtons() {
        val loginBtn = btnLogin as Button
        val fbLogin = btFbLogin as LoginButton
        fbLogin.setReadPermissions(Arrays.asList("email"))

        loginBtn.setOnClickListener { login() }

        fbLogin.registerCallback(callbackManager) {

            onSuccess {
                if (it == null) {
                    throw Exception(getString(fb_login_token_null))
                }
                val loginToken = it.accessToken
                loginPresenter.handleFacebookAccesToken(loginToken)
            }

            onError {
                Log.e(TAG, it?.message)
                toast(getString(fb_login_error))
            }

            onCancel {
                Log.e(TAG, getString(fb_login_cancel))
                toast(getString(fb_login_cancel))
            }
        }
    }

    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        var user: FirebaseUser? = mAuth.currentUser
        if (user != null) {
            startActivity(Intent(this, MyEventsViewImpl::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun login() {
        val email = EtEmail.text.toString()
        val pass = EtPwd.text.toString()

        if (emailOrPasswordAreWrong(email, pass)) return

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        toast(login_correct)
                        startActivity(Intent(this, MyEventsViewImpl::class.java))
                    } else {
                        toast(login_incorrect)
                    }
                }
    }

    fun emailOrPasswordAreWrong(email: String, pass: String): Boolean {

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EtEmail.error = getString(error_invalid_email)
            return true

        }

        if (TextUtils.isEmpty(pass)) {
            EtPwd.error = getString(error_field_required)
            return true
        }
        return false
    }

    private fun injectDependency() {
        val activityComponent = DaggerLoginComponent.builder()
                .loginModule(LoginModule(this))
                .build()

        activityComponent.inject(this)
    }
}

