package cn.qianlicao.gank.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by dongyayun on 16/3/13.
 */

class RetrofitClient {

    companion object {
        val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").serializeNulls().create()

        val retrofit: Retrofit  by lazy(LazyThreadSafetyMode.SYNCHRONIZED, {
            val okHttpBuild = OkHttpClient.Builder()
                    .connectTimeout(8, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)

            Retrofit.Builder()
                    .client(okHttpBuild.build())
                    .baseUrl("http://gank.io")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        })

        val client: GankInterface by lazy(LazyThreadSafetyMode.SYNCHRONIZED, {
            retrofit.create(GankInterface::class.java)
        })


        val clientRx: GankInterfaceRx by lazy(LazyThreadSafetyMode.SYNCHRONIZED, {

            retrofit.create(GankInterfaceRx::class.java)
        })
    }
}