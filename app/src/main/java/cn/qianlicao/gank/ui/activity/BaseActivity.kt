package cn.qianlicao.gank.ui.activity

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by dongyayun on 16/3/13.
 */

open abstract class BaseActivity : AppCompatActivity() {


    abstract fun myContentViewId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myContentViewId())
    }

    fun startActivitySafety(intent: Intent) {
        try {
            startActivity(intent)
        } catch(e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}