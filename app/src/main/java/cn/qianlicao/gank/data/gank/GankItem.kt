package cn.qianlicao.gank.data.gank

import java.io.Serializable
import java.util.*

/**
 * Created by dongyayun on 16/3/13.
 */
class GankItem() : BaseData(), Serializable {
    /**
     * _id : 56e23cea67765966681b3a29
     * _ns : ganhuo
     * createdAt : 2016-03-11T11:35:06.952Z
     * desc : Android内存优化之OOM
     * publishedAt : 2016-03-11T12:37:20.4Z
     * source : chrome
     * type : Android
     * url : http://hukai.me/android-performance-oom/
     * used : true
     * who : SparkYuan
     */

    var _id: String? = null
    var _ns: String? = null
    var createdAt: String? = null
    var desc: String? = null
    var publishedAt: Date? = null
    var source: String? = null
    var type: String? = null
    var url: String? = null
    var isUsed: Boolean = false
    var who: String? = null

}