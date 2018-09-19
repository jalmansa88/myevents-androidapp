package myevents.almansa.unir.es.myevents.utils

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.login.widget.LoginButton
import myevents.almansa.unir.es.myevents.model.Event
import myevents.almansa.unir.es.myevents.utils.functional_clases.F_FacebookCallback

fun Activity.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}

fun View.snackBar(msg: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, msg, duration).show()
}

fun Event.getRolName(): String {

    if (this.role == 0) {
        return "Anonymous"

    } else if (this.role == 1) {
        return "User"

    } else if (this.role == 2) {
        return "VIP"

    } else if (this.role == 3) {
        return "Admin"

    } else if (this.role == 4) {
        return "Service Provider"
    } else {
        return "Unkown"
        //TODO: Throw exception
    }
}

fun LoginButton.registerCallback(
        manager: CallbackManager,
        func: F_FacebookCallback.() -> Unit) {

        val result = F_FacebookCallback()
        result.func()
        registerCallback(manager, result)
}
