package cn.qianlicao.gank.retrofit

import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.DayResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by dongyayun on 16/3/12.
 */
interface GankInterface {
    @GET("api/data/{category}/" + 15 + "/{page}") abstract fun getCategoryData(
            @Path("category") category: String,
            @Path("page") page: Int): Call<CategoryResults>

    @GET("api/day/{year}/{month}/{day}") abstract fun getGankData(
            @Path("year") year: Int,
            @Path("month") month: Int,
            @Path("day") day: Int): Call<DayResults>
}
