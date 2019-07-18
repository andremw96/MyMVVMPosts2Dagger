package andreamw.com.mymvvmposts2dagger.di.component

import andreamw.com.mymvvmposts2dagger.di.module.NetworkModule
import andreamw.com.mymvvmposts2dagger.views.MainListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {

    fun inject(mainListViewModel: MainListViewModel)

    @Component.Builder
    interface Builder {
        fun build() : ViewModelInjector

        fun networkModule(networkModule : NetworkModule) : Builder
    }
}