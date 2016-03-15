package cn.qianlicao.gank.fragment

/**
 * Created by dongyayun on 16/3/14.
 */

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.adapter.CategoryItemsAdapter
import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import com.github.florent37.materialviewpager.MaterialViewPagerHelper
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter

/**
 * Created by florentchampigny on 24/04/15.
 */
class RecyclerViewFragment private constructor(c: Category) : Fragment() {
    lateinit var mRecyclerView: RecyclerView
    lateinit var mAdapter: RecyclerViewMaterialAdapter
    private val category: Category = c
    private val categoryResults: CategoryResults = CategoryResults()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_categary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view!!.findViewById(R.id.recyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(activity)
        mRecyclerView.layoutManager = layoutManager
        mRecyclerView.setHasFixedSize(true)

        mAdapter = RecyclerViewMaterialAdapter(CategoryItemsAdapter(categoryResults))
        mRecyclerView.adapter = mAdapter

        MaterialViewPagerHelper.registerRecyclerView(activity, mRecyclerView, null)
    }

    companion object {

        fun create(catetgory: Category): RecyclerViewFragment = RecyclerViewFragment(catetgory)

    }
}
