package myevents.almansa.unir.es.myevents.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import myevents.almansa.unir.es.myevents.R
import myevents.almansa.unir.es.myevents.utils.toast
import myevents.almansa.unir.es.myevents.view.impl.MyEventsViewImpl

class LoginView : AppCompatActivity() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginBtn = btnLogin as Button

        loginBtn.setOnClickListener { login() }
    }


    private fun login() {
        val email = EtEmail.text.toString()
        val pass = EtPwd.text.toString()

        if (!areEmailAndPwdValid(email, pass)) {
            toast("Please, fill email and password")
            return
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, OnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(this, MyEventsViewImpl:: class.java))
                toast("successful login")
            } else {
                toast("incorrect login")
            }
        })

    }

    private fun areEmailAndPwdValid(email: String, pass: String): Boolean {
        return !TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)
//        Patterns.EMAIL_ADDRESS.matcher(email).matches()
//        editText.setError("no valid input")
    }
}
