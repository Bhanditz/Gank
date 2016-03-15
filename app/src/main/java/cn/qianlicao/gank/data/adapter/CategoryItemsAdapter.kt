package cn.qianlicao.gank.data.adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.qianlicao.gank.R
import cn.qianlicao.gank.data.gank.CategoryResults
import cn.qianlicao.gank.data.gank.GankItem
import org.jetbrains.anko.onClick

/**
 * Created by dongyayun on 16/3/14.
 */
class CategoryItemsAdapter(internal var categoryResults: CategoryResults) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var results: List<GankItem> = categoryResults.results


    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return myViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    }


    class myViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var des: TextView
        lateinit var card: CardView

        init {
            des = view.findViewById(R.id.des) as TextView
            card = view.findViewById(R.id.card_view) as CardView

            des.onClick { des.text = "OnClick" }
        }


    }
}
