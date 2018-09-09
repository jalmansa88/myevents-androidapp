package myevents.almansa.unir.es.myevents.di.Modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import myevents.almansa.unir.es.myevents.model.impl.EventImagesModelImpl
import myevents.almansa.unir.es.myevents.presenter.impl.EventImagesPresenterImpl
import myevents.almansa.unir.es.myevents.presenter.interfaces.EventImagesPresenter

@Module
class EventImagesModule(private var activity: Activity) {

    @Provides
    fun providesActivity(): Activity {
        return activity
    }

    @Provides
    fun providesPresenter(): EventImagesPresenter {
        return EventImagesPresenterImpl(EventImagesModelImpl())
    }

}