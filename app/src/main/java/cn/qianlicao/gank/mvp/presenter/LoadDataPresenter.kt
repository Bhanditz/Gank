package cn.qianlicao.gank.mvp.presenter

import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.DayResults
import cn.qianlicao.gank.mvp.view.BaseView

/**
 * Created by dongyayun on 16/3/13.
 */
interface LoadDataPresenter {
    fun loadCategory(category: Category, page: Int)
    fun loadCategoryFinish(results: CategoryResults)
    fun loadDay(year: Int, month: Int, day: Int)
    fun loadDayFinish(dayResults: DayResults)
    fun bind(view: BaseView)
    fun onError(e: Throwable)
}