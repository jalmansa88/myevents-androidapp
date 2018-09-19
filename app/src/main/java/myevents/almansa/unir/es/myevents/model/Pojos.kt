package myevents.almansa.unir.es.myevents.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class User(var uid: String = "", var email: String = "", var firstname: String = "", var lastname: String = "", var isSp: Boolean = false, var phone: String = "")
@Parcelize data class Event (var uid: String = "", var name: String = "", var description: String = "", var role: Int = 0) : Parcelable
data class Attendee(var uid: String = "", var event_uid: String = "", var user_uid: String = "", var role: Int = 0)
@Parcelize data class Token(var uid: String = "", var eventId: String = "", var role: Int = 0, var value: String = "", var timestamp: Long = 0L): Parcelable
data class Img(var event_uid: String = "", @field:JvmField var isVip: Boolean = false , var nombre: String = "", var url: String = "")