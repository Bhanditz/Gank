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
                    SaveResults.save(categoryResults, isLoadMore)
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
        //Log.d("Testttt", call.toString())

        call.enqueue(object : Callback<CategoryResults> {
            override fun onResponse(p0: Call<CategoryResults>?, p1: Response<CategoryResults>?) {

                val l = p1?.body();

                l?.results!!.forEach { Log.d("Testttt", it.desc) }
            }

            override fun onFailure(p0: Call<CategoryResults>?, p1: Throwable?) {

                Log.d("Testttt", p1.toString())
            }

        })
    }

    fun testGank() {
        var call: Call<DayResults> = RetrofitClient.client.getGankData(2016, 3, 11)
        call.enqueue(object : Callback<DayResults> {
            override fun onResponse(p0: Call<DayResults>?, p1: Response<DayResults>?) {


                Log.d("Testttt", p1!!.headers().toString())


                Log.d("Testttt", p1!!.raw().code().toString())

                val l = p1.body();


                if (l.category != null) {
                    Log.d("Testttt", "nothing")
                    l.category?.forEach { Log.d("Testttt", it) }
                }


                if (l.results?.Android != null) {
                    Log.d("Testttt", "android not none")
                }

                if (l.results?.iOS != null) {
                    Log.d("Testttt", "ios not none")
                }

                if (l.results?.休息视频 != null) {
                    Log.d("Testttt", "休息视频 not none")
                }

                if (l.results?.前端 != null) {
                    Log.d("Testttt", "前端 not none")
                }

                if (l.results?.拓展资源 != null) {
                    Log.d("Testttt", "拓展资源 not none")
                }

                if (l.results?.瞎推荐 != null) {
                    Log.d("Testttt", "results not none")
                }


                if (l.results?.福利 != null) {
                    Log.d("Testttt", "瞎推荐 not none")
                }
                l?.results?.Android?.forEach { Log.d("Testttt", it.url) }


            }

            override fun onFailure(p0: Call<DayResults>?, p1: Throwable?) {

                Log.d("Testttt", p1.toString())
            }

        })
    }

}