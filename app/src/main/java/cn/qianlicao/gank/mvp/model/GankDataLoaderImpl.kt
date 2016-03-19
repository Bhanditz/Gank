package cn.qianlicao.gank.mvp.model

import android.util.Log
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.DayResults
import cn.qianlicao.gank.data.gank.GankItem
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenter
import cn.qianlicao.gank.retrofit.RetrofitClient
import cn.qianlicao.gank.util.SaveResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 * Created by dongyayun on 16/3/13.
 */
class GankDataLoaderImpl constructor(p: LoadDataPresenter) : GankDataLoader {

    val TAG = "GankDataLoaderImpl"

    val presenter: LoadDataPresenter = p

    override fun loadDay(year: Int, month: Int, day: Int) {
        throw UnsupportedOperationException()
    }


    override fun loadCategory(category: Category, page: Int, isLoadMore: Boolean) {
        RetrofitClient.clientRx.
                getCategoryData(category.cname, page)
                .subscribeOn(Schedulers.io())
                .map { result -> result.results }
                .flatMap { data -> Observable.from(data) }
                .observeOn(Schedulers.computation())
                .toSortedList { gankItem, gankItem1 -> gankItem1.publishedAt?.compareTo(gankItem.publishedAt) }
                .doOnNext { items -> items.forEach { Log.d(TAG, it.desc + it.publishedAt.toString()) } }
                .doOnNext { items ->
                    var categoryResults: CategoryResults = CategoryResults()
                    (categoryResults.results as ArrayList).addAll(items!!)
                    categoryResults.category = category
                    categoryResults.pages = page
                    if (!isLoadMore)
                        SaveResults.save(categoryResults)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<GankItem>>() {
                    override fun onNext(p0: List<GankItem>?) {
                        var categoryResults: CategoryResults = CategoryResults()
                        (categoryResults.results as ArrayList).addAll(p0!!)
                        categoryResults.category = category
                        categoryResults.pages = page
                        presenter.loadCategoryFinish(categoryResults, isLoadMore)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(p0: Throwable?) {
                        Log.d(TAG, p0?.toString())
                    }
                })
    }


    fun testApi() {
        var call: Call<CategoryResults> = RetrofitClient.client.getCategoryData(Category.ANDROID.cname, 10)
        //Log.d(TAG, call.toString())

        call.enqueue(object : Callback<CategoryResults> {
            override fun onResponse(p0: Call<CategoryResults>?, p1: Response<CategoryResults>?) {

                val l = p1?.body();

                l?.results!!.forEach { Log.d(TAG, it.desc) }
            }

            override fun onFailure(p0: Call<CategoryResults>?, p1: Throwable?) {

                Log.d(TAG, p1.toString())
            }

        })
    }

    fun testGank() {
        var call: Call<DayResults> = RetrofitClient.client.getGankData(2016, 3, 11)
        call.enqueue(object : Callback<DayResults> {
            override fun onResponse(p0: Call<DayResults>?, p1: Response<DayResults>?) {


                Log.d(TAG, p1!!.headers().toString())


                Log.d(TAG, p1!!.raw().code().toString())

                val l = p1.body();


                if (l.category != null) {
                    Log.d(TAG, "nothing")
                    l.category?.forEach { Log.d(TAG, it) }
                }


                if (l.results?.Android != null) {
                    Log.d(TAG, "android not null")
                }

                if (l.results?.iOS != null) {
                    Log.d(TAG, "ios not null")
                }

                if (l.results?.休息视频 != null) {
                    Log.d(TAG, "休息视频 not null")
                }

                if (l.results?.前端 != null) {
                    Log.d(TAG, "前端 not null")
                }

                if (l.results?.拓展资源 != null) {
                    Log.d(TAG, "拓展资源 not null")
                }

                if (l.results?.瞎推荐 != null) {
                    Log.d(TAG, "results not null")
                }


                if (l.results?.福利 != null) {
                    Log.d(TAG, "瞎推荐 not null")
                }
                l?.results?.Android?.forEach { Log.d(TAG, it.url) }


            }

            override fun onFailure(p0: Call<DayResults>?, p1: Throwable?) {

                Log.d(TAG, p1.toString())
            }

        })
    }

}