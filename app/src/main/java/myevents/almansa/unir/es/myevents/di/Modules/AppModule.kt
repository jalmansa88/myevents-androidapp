package myevents.almansa.unir.es.myevents.di.Modules

import android.app.Application
import dagger.Module
import dagger.Provides
import myevents.almansa.unir.es.myevents.App
import myevents.almansa.unir.es.myevents.di.PerApplication
import myevents.almansa.unir.es.myevents.model.MyEventsModelImpl
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenter
import myevents.almansa.unir.es.myevents.presenter.MyEventsPresenterImpl
import javax.inject.Singleton

@Module
class AppModule(private val app: App) {

    @Provides
    @Singleton
    @PerApplication
    fun provideApp(): Application {
        return app
    }
}