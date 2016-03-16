package cn.qianlicao.gank.mvp.view

import cn.qianlicao.gank.data.gank.DayResults

/**
 * Created by dongyayun on 16/3/16.
 */
open interface DayView : BaseView {
    fun loadDayFinish(dayResults: DayResults)
}