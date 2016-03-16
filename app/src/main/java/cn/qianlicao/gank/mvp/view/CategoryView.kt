package cn.qianlicao.gank.mvp.view

import cn.qianlicao.gank.data.gank.CategoryResults

/**
 * Created by dongyayun on 16/3/16.
 */
open interface CategoryView : BaseView {
    fun loadCategoryFinish(results: CategoryResults)
}