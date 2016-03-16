package cn.qianlicao.gank.mvp.view

/**
 * Created by dongyayun on 16/3/13.
 */
open interface BaseView {
    fun start();
    fun onError(e: Throwable)
}