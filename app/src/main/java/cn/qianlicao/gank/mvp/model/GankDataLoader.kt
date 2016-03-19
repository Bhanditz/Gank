package cn.qianlicao.gank.mvp.model

import cn.qianlicao.gank.data.gank.Category

/**
 * Created by dongyayun on 16/3/13.
 */
interface GankDataLoader {
    fun loadCategory(category: Category, page: Int, isLoadMore: Boolean = false)
    fun loadDay(year: Int, month: Int, day: Int)
}