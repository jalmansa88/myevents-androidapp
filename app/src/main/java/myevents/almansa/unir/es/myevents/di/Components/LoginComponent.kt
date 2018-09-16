package myevents.almansa.unir.es.myevents.di.Components

import dagger.Component
import myevents.almansa.unir.es.myevents.di.Modules.LoginModule
import myevents.almansa.unir.es.myevents.view.impl.LoginViewImpl

@Component(modules = arrayOf(LoginModule::class))
interface LoginComponent {

    fun inject(loginView: LoginViewImpl)
}