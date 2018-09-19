package myevents.almansa.unir.es.myevents.di.Modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import myevents.almansa.unir.es.myevents.model.impl.LoginModelImpl
import myevents.almansa.unir.es.myevents.presenter.impl.LoginPresenterImpl
import myevents.almansa.unir.es.myevents.presenter.interfaces.LoginPresenter

@Module
class LoginModule(private var activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }

    @Provides
    fun providesPresenter(): LoginPresenter {
        return LoginPresenterImpl(LoginModelImpl())
    }

}