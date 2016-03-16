package cn.qianlicao.gank.ui.fragment

/**
 * Created by dongyayun on 16/3/14.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.adapter.CategoryItemsAdapter
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenter
import cn.qianlicao.gank.mvp.presenter.LoadDataPresenterImpl
import cn.qianlicao.gank.mvp.view.CategoryView
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter

/**
 * Created by florentchampigny on 24/04/15.
 */
class RecyclerViewFragment private constructor(c: Category) : Fragment(), CategoryView {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerViewMaterialAdapter
    lateinit var realAdapter: CategoryItemsAdapter
    private val category: Category = c
    private val categoryResults: CategoryResults = CategoryResults()
    private val loadDataPresenter: LoadDataPresenter = LoadDataPresenterImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_categary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view!!.findViewById(R.id.recyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)

        realAdapter = CategoryItemsAdapter(categoryResults);

        mAdapter = RecyclerViewMaterialAdapter(realAdapter)

        mRecyclerView.adapter = mAdapter

        MaterialViewPagerHelper.registerRecyclerView(activity, mRecyclerView, null)

        loadDataPresenter.bind(this)

        loadDataPresenter.loadCategory(category, 1)
    }


    override fun onError(e: Throwable) {
    }

    override fun loadCategoryFinish(results: CategoryResults) {
        if (!category.equals(results.category))
            return
        realAdapter.addAll(results.results)
        mAdapter.notifyDataSetChanged()

    }

    override fun onPause() {
        super.onPause()
        Log.d("GankTest", "onPause" + category.cname)
    }

    override fun onStart() {
        super.onStart()
        Log.d("GankTest", "onStart" + category.cname)
    }

    override fun onResume() {
        super.onResume()
        Log.d("GankTest", "onResume" + category.cname)
    }

    override fun start() {
    }

    companion object {

        fun create(catetgory: Category): RecyclerViewFragment = RecyclerViewFragment(catetgory)

    }
}
