package cn.qianlicao.gank.ui.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.widget.TextView
import android.widget.Toast
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.ui.fragment.RecyclerViewFragment
import com.github.florent37.materialviewpager.MaterialViewPager
import com.github.florent37.materialviewpager.header.HeaderDesign

class MainActivity : BaseActivity() {
    override fun myContentViewId() = R.layout.activity_main

    val mViewPager: MaterialViewPager by lazy { findViewById(R.id.materialViewPager) as MaterialViewPager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
        Thread() {
            testApiRx()
        }.start()
        */
        initTabs()
    }


    fun initTabs() {

        var logo: TextView = findViewById(R.id.logo_white) as TextView;
        logo.setOnClickListener { view ->
            mViewPager.notifyHeaderChanged();
            Toast.makeText(applicationContext, "Yes, the title is clickable", Toast.LENGTH_SHORT).show()
        }

        mViewPager.viewPager.adapter = object : FragmentStatePagerAdapter(supportFragmentManager) {
            override fun getItem(p0: Int): Fragment? {
                return RecyclerViewFragment.create(Category.values().get(p0))
            }

            override fun getCount() = Category.values().size

            override fun getPageTitle(position: Int): CharSequence? {
                return Category.values().get(position).cname
            }

        };

        mViewPager.setMaterialViewPagerListener { page ->

            logo.text = Category.values().get(page).cname

            when (page % 3) {
                0 -> HeaderDesign.fromColorResAndUrl(
                        R.color.green,
                        "https://fs01.androidpit.info/a/63/0e/android-l-wallpapers-630ea6-h900.jpg");
                1 -> HeaderDesign.fromColorResAndUrl(
                        R.color.blue,
                        "http://cdn1.tnwcdn.com/wp-content/blogs.dir/1/files/2014/06/wallpaper_51.jpg");
                2 -> HeaderDesign.fromColorResAndUrl(
                        R.color.cyan,
                        "http://www.droid-life.com/wp-content/uploads/2014/10/lollipop-wallpapers10.jpg");
                else -> HeaderDesign.fromColorResAndUrl(
                        R.color.red,
                        "http://www.tothemobile.com/wp-content/uploads/2014/07/original.jpg");
            }
        }

        mViewPager.viewPager.offscreenPageLimit = mViewPager.viewPager.adapter.count;
        mViewPager.pagerTitleStrip.setViewPager(mViewPager.viewPager);

        var toolbar: Toolbar = mViewPager.toolbar;

        if (toolbar != null && supportActionBar != null) {
            setSupportActionBar(toolbar);

            var actionBar: ActionBar = supportActionBar!!;
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.title = ""
        }
    }
}
