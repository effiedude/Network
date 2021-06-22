package com.townspriter.android.base.network;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/******************************************************************************
 * @path RxNetwork:AbstractServiceManager
 * @version 1.0.0.0
 * @describe 基础数据服务请求
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 11:29:43
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public abstract class AbstractServiceManager
{
    private static final int DEFAULTxCONNECTxTIMExOUTxSECOND=5;
    private static final int DEFAULTxREADxTIMExOUTxSECOND=5;
    private static final int DEFAULTxWRITExTIMExOUTxSECOND=5;
    private @Nullable final Retrofit mRetrofit;
    private HTTPHeaderInterceptor.Builder mHttpHeaderBuilder;
    private HTTPHeaderInterceptor.OnInterceptListener mInterceptListener=headerParamsMap->onInterceptHeaderParams(headerParamsMap);
    
    public AbstractServiceManager(@Nullable String baseUrl)
    {
        OkHttpClient.Builder httpClientBuilder=configHTTPClientBuilder();
        if(null==httpClientBuilder)
        {
            httpClientBuilder=createNormalHTTPClientBuilder();
        }
        mHttpHeaderBuilder=configHTTPHeaderBuilder();
        if(null==mHttpHeaderBuilder)
        {
            mHttpHeaderBuilder=createNormalHTTPHeaderBuilder();
        }
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor(new HTTPLogInterceptor(enableLogcat()));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.addInterceptor(mHttpHeaderBuilder.build());
        Retrofit.Builder retrofitBuilder=new Retrofit.Builder();
        mRetrofit=retrofitBuilder.client(httpClientBuilder.build()).addConverterFactory(processBaseConfig()?GsonConverterFactory.create():GSonConverterFactoryFat.create(configBaseConfigType())).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).baseUrl(baseUrl).build();
    }
    
    /**
     * enableLogcat
     * 是否打印网络请求日志
     * 
     * @return true:是 false:否
     */
    public abstract boolean enableLogcat();
    
    /**
     * processBaseConfig
     * <p>
     * 是否自行解析公共返回参数
     * 
     * @return true:是 false:否(默认)
     */
    public abstract boolean processBaseConfig();
    
    /**
     * configHTTPClientBuilder
     * <p>
     * 初始化网络请求<br>
     * 可定制网络请求参数<br>
     * 如果不自定义则使用默认配置
     * 
     * @return OkHttpClient.Builder
     */
    public abstract @NonNull OkHttpClient.Builder configHTTPClientBuilder();
    
    /**
     * configHTTPHeaderBuilder
     * <p>
     * 初始化网络请求公参<br>
     * 可定制网络请求公参<br>
     * 如果不自定义则使用默认配置
     * 
     * @return HTTPHeaderInterceptor.Builder
     */
    public abstract @Nullable HTTPHeaderInterceptor.Builder configHTTPHeaderBuilder();
    
    /**
     * configBaseConfigType
     * <p>
     * 是否自定义头文件解析类
     *
     * @return 自定义头文件策略
     */
    public @NonNull abstract IConfig configBaseConfigType();
    
    /**
     * onInterceptHeaderParams
     * 返回头信息结构体并允许用户插入新的信息
     *
     * @param headerParamsMap
     * 请求头信息
     */
    public void onInterceptHeaderParams(Map<String,Object> headerParamsMap)
    {}
    
    public void addHeaderParams(@NonNull String key,@NonNull Object value)
    {
        mHttpHeaderBuilder.addHeaderParams(key,String.valueOf(value));
    }
    
    public void addHeaderParams(@NonNull Map<String,Object> params)
    {
        if(params!=null&&params.size()>0)
        {
            mHttpHeaderBuilder.addHeaderParams(params);
        }
    }
    
    public @Nullable <T> T createService(Class<T> service)
    {
        if(null!=mRetrofit)
        {
            return mRetrofit.create(service);
        }
        return null;
    }
    
    private OkHttpClient.Builder createNormalHTTPClientBuilder()
    {
        // 初始化网络请求公共参数
        OkHttpClient.Builder httpClientBuilder=new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULTxCONNECTxTIMExOUTxSECOND,TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULTxREADxTIMExOUTxSECOND,TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULTxWRITExTIMExOUTxSECOND,TimeUnit.SECONDS);
        return httpClientBuilder;
    }
    
    private HTTPHeaderInterceptor.Builder createNormalHTTPHeaderBuilder()
    {
        // 添加网络请求头部参数
        HTTPHeaderInterceptor.Builder httpHeaderBuilder=new HTTPHeaderInterceptor.Builder();
        httpHeaderBuilder.setOnInterceptListener(mInterceptListener);
        httpHeaderBuilder.addHeaderParams("Content-Type","application/json");
        return httpHeaderBuilder;
    }
}
