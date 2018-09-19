package myevents.almansa.unir.es.myevents.di.Components

import dagger.Component
import myevents.almansa.unir.es.myevents.di.Modules.EventImagesModule
import myevents.almansa.unir.es.myevents.di.Modules.MyEventsModule
import myevents.almansa.unir.es.myevents.view.impl.EventImagesViewImpl
import myevents.almansa.unir.es.myevents.view.impl.MyEventsViewImpl

@Component(modules = arrayOf(EventImagesModule::class))
interface EventImagesComponent {

    fun inject(imagesView: EventImagesViewImpl)
}