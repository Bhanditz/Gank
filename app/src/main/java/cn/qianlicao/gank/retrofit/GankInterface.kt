package cn.qianlicao.gank.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by dongyayun on 16/3/12.
 */
interface GankInterface {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String): Call<List<String>>
}
