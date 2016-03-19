package cn.qianlicao.gank.util

import cn.qianlicao.gank.data.gank.Category
import cn.qianlicao.gank.data.gank.CategoryResults
import java.io.*

/**
 * Created by dongyayun on 16/3/20.
 */
class SaveResults {
    companion object {

        val PATH = "/data/data/cn.qianlicao.gank/files/"

        fun save(categoryResults: CategoryResults) {

            val stream: FileOutputStream = FileOutputStream(File(PATH + categoryResults.category.cname + ".cache"))
            val objectOutputStream: ObjectOutputStream = ObjectOutputStream(stream)
            objectOutputStream.writeObject(categoryResults)

        }


        fun read(category: Category): CategoryResults {
            try {
                val stream: FileInputStream = FileInputStream(File(PATH + category.cname + ".cache"))
                val inputStream: ObjectInputStream = ObjectInputStream(stream);

                return inputStream.readObject() as CategoryResults
            } catch(e: FileNotFoundException) {
                return CategoryResults()
            }
        }
    }
}