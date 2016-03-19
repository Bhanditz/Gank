package cn.qianlicao.gank.ui.activity

import android.os.Bundle
import cn.qianlicao.gank.R

class MainActivity : BaseActivity() {
    override fun myContentViewId() = R.layout.activity_main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Thread() {
            testApiRx()
        }.start()
        */
    }


}
