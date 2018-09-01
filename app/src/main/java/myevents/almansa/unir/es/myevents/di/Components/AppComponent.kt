package myevents.almansa.unir.es.myevents.di.Components

import dagger.Component
import myevents.almansa.unir.es.myevents.App
import myevents.almansa.unir.es.myevents.di.Modules.AppModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: App)
}