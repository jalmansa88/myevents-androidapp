package myevents.almansa.unir.es.myevents.di.Components

import dagger.Component
import myevents.almansa.unir.es.myevents.di.Modules.MyEventsModule
import myevents.almansa.unir.es.myevents.view.MyEventsViewImpl

@Component(modules = arrayOf(MyEventsModule::class))
interface MyEventsComponent {

    fun inject(myEventsView: MyEventsViewImpl)
}