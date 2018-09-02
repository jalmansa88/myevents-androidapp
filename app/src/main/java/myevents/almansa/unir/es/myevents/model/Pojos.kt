package myevents.almansa.unir.es.myevents.model

data class User(var uid: String = "", var email: String = "", var firstname: String = "", var lastname: String = "", var isSp: Boolean = false, var phone: String = "")
data class Event(var uid: String = "", var name: String = "", var description: String = "", var role: Int = 0)
data class Attendee(var uid: String = "", var event_uid: String = "", var user_uid: String = "", var role: Int = 0)
data class Token(var uid: String = "", var eventUid: String = "", var role: Int = 0, var varue: String = "", var timestamp: Long = 0L)