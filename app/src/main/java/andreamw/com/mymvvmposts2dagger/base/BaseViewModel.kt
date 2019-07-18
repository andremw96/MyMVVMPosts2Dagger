package andreamw.com.mymvvmposts2dagger.base

import andreamw.com.mymvvmposts2dagger.di.component.DaggerViewModelInjector
import andreamw.com.mymvvmposts2dagger.di.component.ViewModelInjector
import andreamw.com.mymvvmposts2dagger.di.module.NetworkModule
import andreamw.com.mymvvmposts2dagger.model.Post
import andreamw.com.mymvvmposts2dagger.views.MainListViewModel
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private val injector : ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when(this) {
            is MainListViewModel -> injector.inject(this)
        }
    }

    abstract fun onRetrievePostListStart()
    abstract fun onRetrievePostListFinish()
    abstract fun onRetrievePostListSuccess(list: List<Post>)
    abstract fun onRetrievePostListError()
}