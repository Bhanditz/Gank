package cn.qianlicao.gank.data.gank

import java.io.Serializable
import java.util.*

/**
 * Created by dongyayun on 16/3/13.
 */

class CategoryResults : Serializable {

    companion object {
        @JvmField val serialVersionUID = 6201933920870624595L
    }

    var category: Category = Category.ANDROID
    var results: List<GankItem> = ArrayList()
}