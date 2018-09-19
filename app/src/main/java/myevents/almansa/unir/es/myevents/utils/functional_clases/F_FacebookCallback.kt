package myevents.almansa.unir.es.myevents.utils.functional_clases

import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult

class F_FacebookCallback: FacebookCallback<LoginResult> {

    private var f_onSuccess: ((result: LoginResult?) -> Unit)? = null
    private var f_onCancel: (() -> Unit)? = null
    private var f_onError: ((error: FacebookException?) -> Unit)? = null

    override fun onSuccess(result: LoginResult?) {
        f_onSuccess?.invoke(result)
    }

    fun onSuccess(func: (result: LoginResult?) -> Unit) {
        f_onSuccess = func
    }

    override fun onCancel() {
        f_onCancel?.invoke()
    }

    fun onCancel(func: () -> Unit) {
        f_onCancel = func
    }

    override fun onError(error: FacebookException?) {
        f_onError?.invoke(error)
    }

    fun onError(func: (error: FacebookException?) -> Unit) {
        f_onError = func
    }
}