package myevents.almansa.unir.es.myevents.model

data class User(val uid: String, val email: String, var firstname: String, var lastname: String, var phone: String)
data class Event(val uid: String, var name: String, var description: String)
data class Attendee(var uid: String, val eventUid: String, val userUid: String, val role: Int)
data class Token(val uid: String, val eventUid: String, val role: Int, val value: String, val timestamp: Long)