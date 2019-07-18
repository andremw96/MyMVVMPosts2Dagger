package andreamw.com.mymvvmposts2dagger.api

import andreamw.com.mymvvmposts2dagger.model.Post
import io.reactivex.Observable
import retrofit2.http.GET

interface PostApi {
    
    @GET("/posts")
    fun getPosts() : Observable<List<Post>>

}