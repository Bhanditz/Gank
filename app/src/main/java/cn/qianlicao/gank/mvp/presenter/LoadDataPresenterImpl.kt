package cn.qianlicao.gank.mvp.presenter

import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.DayResults
import cn.qianlicao.gank.mvp.model.GankDataLoader
import cn.qianlicao.gank.mvp.model.GankDataLoaderImpl
import cn.qianlicao.gank.mvp.view.BaseView
import cn.qianlicao.gank.mvp.view.CategoryView
import cn.qianlicao.gank.mvp.view.DayView

/**
 * Created by dongyayun on 16/3/13.
 */
class LoadDataPresenterImpl : LoadDataPresenter {

    lateinit var bindView: BaseView;

    val loader: GankDataLoader = GankDataLoaderImpl(this)


    override fun bind(view: BaseView) {
        bindView = view
    }

    override fun loadCategory(category: Category, page: Int, isLoadMore: Boolean) {
        loader.loadCategory(category, page, isLoadMore)
    }

    override fun loadCategoryFinish(results: CategoryResults, isLoadMore: Boolean) {
        (bindView as CategoryView).loadCategoryFinish(results, isLoadMore)
    }

    override fun loadDay(year: Int, month: Int, day: Int) {
        loader.loadDay(year, month, day)
    }

    override fun loadDayFinish(dayResults: DayResults) {
        (bindView as DayView).loadDayFinish(dayResults)
    }

    override fun onError(e: Throwable) {
        bindView.onError(e)
    }
}