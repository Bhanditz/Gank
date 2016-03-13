package cn.qianlicao.gank.activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle

/**
 * Created by dongyayun on 16/3/13.
 */

open abstract class BaseActivity : Activity() {


    abstract fun myContentViewId(): Int


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(myContentViewId())


    }

    fun startActivitySafety(clazz: Class<Activity>) {
        var intent: Intent = Intent(this, clazz);

        try {
            startActivity(intent)
        } catch(e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
}