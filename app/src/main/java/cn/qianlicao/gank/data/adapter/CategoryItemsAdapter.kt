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
import cn.qianlicao.gank.listens.recyclerViewOnClickListern
import org.jetbrains.anko.onClick
import java.util.*

/**
 * Created by dongyayun on 16/3/14.
 */
class CategoryItemsAdapter(internal var categoryResults: CategoryResults) : RecyclerView.Adapter<CategoryItemsAdapter.myViewHolder>() {

    var itemOnclickListener: recyclerViewOnClickListern? = null

    var results: ArrayList<GankItem> = categoryResults.results  as ArrayList<GankItem>


    override fun getItemCount(): Int {
        return results.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder? {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false)

        return myViewHolder(view)
    }


    override fun onBindViewHolder(holder: myViewHolder, position: Int) {

        val item = results.get(position)

        holder.des.text = item.desc

        val year = item.publishedAt!!.year + 1990
        val month = item.publishedAt!!.month + 1

        holder.publishTime.text = "发布于:" + year + "年" + month + "月" + item.publishedAt?.date + "日"
        holder.author.text = "作者:" + item.who


        holder.card.onClick { itemOnclickListener?.onClick(position) }

    }

    fun insert(gankItem: GankItem, position: Int) {
        results.add(position, gankItem);
        notifyItemInserted(position);
    }

    fun remove(position: Int) {
        results.removeAt(position);
        notifyItemRemoved(position);
    }

    fun clear() {
        val size: Int = results.size;
        results.clear()
        notifyItemRangeRemoved(0, size);
    }

    fun addAll(list: List<GankItem>) {
        val startIndex = results.size;
        results.addAll(startIndex, list);
        notifyItemRangeInserted(startIndex, list.size);
    }

    class myViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var des: TextView
        lateinit var publishTime: TextView
        lateinit var author: TextView
        lateinit var card: CardView

        init {
            des = view.findViewById(R.id.des) as TextView
            card = view.findViewById(R.id.card_view) as CardView
            author = view.findViewById(R.id.author) as TextView
            publishTime = view.findViewById(R.id.publishTime) as TextView
        }


    }
}
