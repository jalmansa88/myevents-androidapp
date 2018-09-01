package myevents.almansa.unir.es.myevents.di.Modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import myevents.almansa.unir.es.myevents.model.MyEventsModelImpl
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenter
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenterImpl

@Module
class MyEventsModule(private var activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }

    @Provides
    fun providesPresenter(): MyEventsPresenter {
        return MyEventsPresenterImpl(MyEventsModelImpl())
    }

}