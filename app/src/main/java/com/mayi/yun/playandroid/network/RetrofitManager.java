package com.mayi.yun.playandroid.network;

import android.util.Log;

import com.blankj.utilcode.util.NetworkUtils;
import com.mayi.yun.playandroid.constant.Constant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者： wh
 * 时间：  2018/2/27
 * 名称：
 * 版本说明：
 * 附加注释：
 * 主要接口：
 */
public class RetrofitManager {
    /**
     * 连接时间
     */
    private static long CONNECT_TIMEOUT = 60L;
    /**
     * 读取时间
     */
    private static long READ_TIMEOUT = 10L;
    /**
     * 写入时间
     */
    private static long WRITE_TIMEOUT = 10L;
    /**
     * 设缓存有效期为1天
     */
    private static final long CACHE_STALE_SEC = 60 * 60 * 24;
    /**
     * 查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
     */
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    /**
     * okhttp
     */
    private static volatile OkHttpClient mOkHttpClient;

    public static  ApiService getApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.REQUEST_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getOkHttpClient())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    /**
     * 云端响应头拦截器，用来配置缓存策略
     * Dangerous interceptor that rewrites the server's cache-control header.
     */
    private static final Interceptor mRewriteCacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isConnected()) {
                //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + CACHE_CONTROL_CACHE)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };

    public static OkHttpClient getOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("okHttp", "Message:" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (mOkHttpClient == null) {
         //   Cache cache = new Cache(new File(App.getAppContext().getCacheDir(), "HttpCache"), 1024 * 1024 * 100);
            mOkHttpClient = new OkHttpClient.Builder()
                   // .cache(cache)
                  //  .addInterceptor(mRewriteCacheControlInterceptor)
                    .addInterceptor(loggingInterceptor)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .build();
        }
        return mOkHttpClient;
    }
}
