package myevents.almansa.unir.es.myevents.model.impl

import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable
import myevents.almansa.unir.es.myevents.model.Token
import myevents.almansa.unir.es.myevents.model.interfaces.LoginModel
import myevents.almansa.unir.es.myevents.utils.Constants

class LoginModelImpl: LoginModel {

    private val db = FirebaseFirestore.getInstance()

    private val tokenRef = db.collection(Constants.TOKENS_TABLE_NAME)

    override fun loadTemporalToken(token: String): Observable<Token> {

        var tokenQuery = tokenRef
                .whereEqualTo(Constants.VALUE, token)
                .whereEqualTo(Constants.ROLE, 0)

        return Observable.create { emitter ->

            tokenQuery.get().addOnCompleteListener {task ->

                if (task.isComplete) {
                    if(task.result.isEmpty){
                        emitter.onComplete()
                        return@addOnCompleteListener
                    }

                    val token = task.result.documents[0].toObject<Token>(Token::class.java)
                    emitter.onNext(token)
                    emitter.onComplete()
                }
            }
        }
    }

}