package myevents.almansa.unir.es.myevents.utils

import android.app.Activity
import android.widget.Toast
import myevents.almansa.unir.es.myevents.model.Event

fun Activity.toast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
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