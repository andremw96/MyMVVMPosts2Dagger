package andreamw.com.mymvvmposts2dagger.views

import andreamw.com.mymvvmposts2dagger.base.BaseViewModel
import andreamw.com.mymvvmposts2dagger.model.Post
import androidx.lifecycle.MutableLiveData

class PostViewModel : BaseViewModel() {

    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    fun bind(post: Post) {
        postTitle.value = post.title
        postBody.value = post.body
    }

    fun getPostTitle() : MutableLiveData<String> {
        return postTitle
    }

    fun getPostBody() : MutableLiveData<String> {
        return postBody
    }

}