package myevents.almansa.unir.es.myevents

import android.app.Application
import myevents.almansa.unir.es.myevents.di.Components.AppComponent
import myevents.almansa.unir.es.myevents.di.Components.DaggerAppComponent
import myevents.almansa.unir.es.myevents.di.Modules.AppModule

class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        setup()
    }

    fun setup() {
        component = DaggerAppComponent.builder()
                .appModule(AppModule(this)).build()
        component.inject(this)
    }

    fun getAppComponent(): AppComponent {
        return component
    }

    companion object {
        lateinit var instance: App private set
    }
}