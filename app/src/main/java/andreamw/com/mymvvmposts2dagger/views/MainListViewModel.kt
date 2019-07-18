package andreamw.com.mymvvmposts2dagger.views

import andreamw.com.mymvvmposts2dagger.R
import andreamw.com.mymvvmposts2dagger.api.PostApi
import andreamw.com.mymvvmposts2dagger.base.BaseViewModel
import andreamw.com.mymvvmposts2dagger.model.Post
import andreamw.com.mymvvmposts2dagger.model.PostDao
import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainListViewModel(private val postDao: PostDao) : BaseViewModel() {

    @Inject
    lateinit var postApi: PostApi

    private lateinit var subscription : Disposable

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage : MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }
    val postListAdapter = PostListAdapter()

    init {
        loadPosts()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadPosts() {
        /*subscription = postApi.getPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )*/

        subscription = Observable.fromCallable{ postDao.all }
            .concatMap {
                dbPostList ->
                    if(dbPostList.isEmpty()){
                        postApi.getPosts().concatMap {
                            apiPostList -> postDao.insertAll(*apiPostList.toTypedArray())
                            Observable.just(apiPostList)
                        }
                    }
                    else {
                        Observable.just(dbPostList)
                    }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe(
                { result -> onRetrievePostListSuccess(result) },
                { onRetrievePostListError() }
            )
    }

    override fun onRetrievePostListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    override fun onRetrievePostListFinish() {
        loadingVisibility.value = View.GONE
    }

    override fun onRetrievePostListSuccess(postList: List<Post>) {
        postListAdapter.updatePostList(postList)
    }

    override fun onRetrievePostListError() {
        errorMessage.value = R.string.post_error
    }
}