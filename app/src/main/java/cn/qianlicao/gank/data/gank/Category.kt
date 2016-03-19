package cn.qianlicao.gank.data.gank

import java.io.Serializable

/**
 * Created by dongyayun on 16/3/13.
 */
enum class Category(val cname: String) : Serializable {
    ANDROID("Android"),
    IOS("iOS"),
    RECOMMEND("瞎推荐"),
    EXTENDRESOURCE("拓展资源"),
    //RESTVIDEO("休息视频"),
    FRONTED("前端"),
    //SEX("福利") ;
}