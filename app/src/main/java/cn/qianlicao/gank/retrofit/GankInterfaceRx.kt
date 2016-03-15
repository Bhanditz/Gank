package cn.qianlicao.gank.retrofit

import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.DayResults
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by dongyayun on 16/3/13.
 */

interface GankInterfaceRx {
    @GET("api/data/{category}/" + 10 + "/{page}") abstract fun getCategoryData(
            @Path("category") category: String,
            @Path("page") page: Int): Observable<CategoryResults>

    @GET("api/day/{year}/{month}/{day}") abstract fun getGankData(
            @Path("year") year: Int,
            @Path("month") month: Int,
            @Path("day") day: Int): Observable<DayResults>
}
