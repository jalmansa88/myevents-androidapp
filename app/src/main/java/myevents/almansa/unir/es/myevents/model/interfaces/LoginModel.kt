package myevents.almansa.unir.es.myevents.model.interfaces

import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Token

interface LoginModel {
    fun loadTemporalToken(token: String): Observable<Token>
}