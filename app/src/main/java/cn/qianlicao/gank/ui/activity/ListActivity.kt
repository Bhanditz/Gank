package cn.qianlicao.gank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.widget.ArrayAdapter
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.adapter.CategoryItemsAdapter
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.listens.recyclerViewOnClickListern
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenter
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenterImpl
import cn.qianlicao.gank.mvp.view.CategoryView
import cn.qianlicao.gank.util.SaveResults
import org.jetbrains.anko.toast

class ListActivity : BaseActivity(), CategoryView, recyclerViewOnClickListern {

    private val menuItems = Category.values()

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.gank_items) as RecyclerView }

    private val swipeRefresh: SwipeRefreshLayout by lazy { findViewById(R.id.swipe_refresh) as SwipeRefreshLayout }

    private val categoryResults: CategoryResults = CategoryResults()
    private val loadDataPresenter: LoadDataPresenter = LoadDataPresenterImpl()

    private var lastVisibleItem: Int = 0

    private var nextPage: Int = 1

    private var currentCategory: Category = Category.ANDROID

    lateinit var realAdapter: CategoryItemsAdapter

    override fun myContentViewId() = R.layout.activity_list

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initSwipeRefresh()
        initRecyclerView()

        loadDataPresenter.bind(this)
    }

    fun initActionBar() {
        supportActionBar?.navigationMode = ActionBar.NAVIGATION_MODE_LIST
        supportActionBar?.title = ""
        val adatper: ArrayAdapter<String> = ArrayAdapter(this, R.layout.category_menu_item, R.id.menu_list_item, menuItems.map { it -> it.cname })

        supportActionBar?.setListNavigationCallbacks(adatper, object : ActionBar.OnNavigationListener {
            override fun onNavigationItemSelected(p0: Int, p1: Long): Boolean {

                currentCategory = menuItems.get(p0)

                val result = SaveResults.read(currentCategory)

                if (result.results.size == 0) {
                    loadDataPresenter.loadCategory(currentCategory, nextPage, false)
                } else {
                    nextPage = result.pages + 1
                    loadCategoryFinish(result)
                }

                toast(currentCategory.cname + " is click")
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



        recyclerView.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == realAdapter.itemCount) {
                    swipeRefresh.isRefreshing = true;
                    loadDataPresenter.loadCategory(currentCategory, nextPage, true)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                lastVisibleItem = (recyclerView!!.layoutManager as LinearLayoutManager).findLastVisibleItemPosition();
            }
        })
    }

    fun initSwipeRefresh() {

        swipeRefresh.setProgressViewOffset(false, 0, TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24f, resources.displayMetrics).toInt());

        swipeRefresh.setOnRefreshListener { loadDataPresenter.loadCategory(currentCategory, 1, false) }

    }

    override fun loadCategoryFinish(results: CategoryResults, isLoadMore: Boolean) {
        swipeRefresh.isRefreshing = false

        nextPage = results.pages + 1

        if (!isLoadMore)
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

        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", item.url)

        startActivity(intent)
    }

}
