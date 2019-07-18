package andreamw.com.mymvvmposts2dagger.di

import andreamw.com.mymvvmposts2dagger.model.AppDatabase
import andreamw.com.mymvvmposts2dagger.views.MainListViewModel
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import java.lang.IllegalArgumentException

class ViewModelFactory (private val activity: AppCompatActivity): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
            @Suppress("UNCHECKED_CAST")
            return MainListViewModel(db.postDao()) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }

}