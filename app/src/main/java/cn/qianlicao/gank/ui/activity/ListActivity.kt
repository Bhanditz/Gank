package cn.qianlicao.gank.ui.activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.adapter.CategoryItemsAdapter
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.listens.recyclerViewOnClickListern
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenter
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenterImpl
import cn.qianlicao.gank.mvp.view.CategoryView
import org.jetbrains.anko.toast

class ListActivity : BaseActivity(), CategoryView, recyclerViewOnClickListern {

    private val menuItems = Category.values()

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.gank_items) as RecyclerView }

    private val categoryResults: CategoryResults = CategoryResults()
    private val loadDataPresenter: LoadDataPresenter = LoadDataPresenterImpl()

    lateinit var realAdapter: CategoryItemsAdapter


    override fun myContentViewId() = R.layout.activity_list


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initRecyclerView()


        loadDataPresenter.bind(this)

    }

    fun initActionBar() {
        supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_LIST
        supportActionBar?.title = ""


        val adatper: ArrayAdapter<String> = ArrayAdapter(this, R.layout.category_menu_item, R.id.menu_list_item, menuItems.map { it -> it.cname })

        supportActionBar?.setListNavigationCallbacks(adatper, object : ActionBar.OnNavigationListener {
            override fun onNavigationItemSelected(p0: Int, p1: Long): Boolean {

                val category = menuItems.get(p0)

                loadDataPresenter.loadCategory(category, 1)

                toast(category.cname + " is click")
                return true
            }

        })
    }

    fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        realAdapter = CategoryItemsAdapter(categoryResults);
        realAdapter.itemOnclickListener = this

        recyclerView.adapter = realAdapter
    }

    override fun loadCategoryFinish(results: CategoryResults) {
        realAdapter.clear()
        realAdapter.addAll(results.results)
    }

    override fun start() {
        throw UnsupportedOperationException()
    }

    override fun onError(e: Throwable) {
        throw UnsupportedOperationException()
    }

    override fun onClick(pos: Int) {
        val item = categoryResults.results.get(pos)
        toast(item.desc + item.who)
    }

}
