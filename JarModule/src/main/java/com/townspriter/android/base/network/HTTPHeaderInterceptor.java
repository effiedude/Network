package com.townspriter.android.base.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import androidx.annotation.Nullable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/******************************************************************************
 * @path RxNetwork:HTTPHeaderInterceptor
 * @version 1.0.0.0
 * @describe 数据请求共参信息配置
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-29 16:48:15
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class HTTPHeaderInterceptor implements Interceptor
{
    private static final int HEADERxPARAMSxMAPxSIZE=32;
    private final Map<String,Object> mHeaderParamsMap;
    private @Nullable OnInterceptListener mInterceptListener;
    
    public HTTPHeaderInterceptor()
    {
        mHeaderParamsMap=new HashMap<>(HEADERxPARAMSxMAPxSIZE);
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        Request oldRequest=chain.request();
        // 新的请求
        Request.Builder requestBuilder=oldRequest.newBuilder();
        requestBuilder.method(oldRequest.method(),oldRequest.body());
        if(mInterceptListener!=null)
        {
            mInterceptListener.onIntercept(mHeaderParamsMap);
        }
        // 添加公共参数到信息头中
        if(mHeaderParamsMap!=null&&mHeaderParamsMap.size()>0)
        {
            for(Map.Entry<String,Object> params:mHeaderParamsMap.entrySet())
            {
                requestBuilder.header(params.getKey(),String.valueOf(params.getValue()));
            }
        }
        Request newRequest=requestBuilder.build();
        return chain.proceed(newRequest);
    }
    
    interface OnInterceptListener
    {
        /**
         * onIntercept
         * 监听头信息变化
         *
         * @param headerParamsMap
         * 头信息结构体
         */
        void onIntercept(Map<String,Object> headerParamsMap);
    }
    public static class Builder
    {
        private final HTTPHeaderInterceptor mHTTPHeaderInterceptor;
        
        public Builder()
        {
            mHTTPHeaderInterceptor=new HTTPHeaderInterceptor();
        }
        
        public Builder setOnInterceptListener(@Nullable OnInterceptListener interceptListener)
        {
            mHTTPHeaderInterceptor.mInterceptListener=interceptListener;
            return this;
        }
        
        public Builder addHeaderParams(String key,String value)
        {
            mHTTPHeaderInterceptor.mHeaderParamsMap.put(key,value);
            return this;
        }
        
        public Builder addHeaderParams(Map<String,Object> params)
        {
            mHTTPHeaderInterceptor.mHeaderParamsMap.putAll(params);
            return this;
        }
        
        public Builder addHeaderParams(String key,int value)
        {
            return addHeaderParams(key,String.valueOf(value));
        }
        
        public Builder addHeaderParams(String key,float value)
        {
            return addHeaderParams(key,String.valueOf(value));
        }
        
        public Builder addHeaderParams(String key,long value)
        {
            return addHeaderParams(key,String.valueOf(value));
        }
        
        public Builder addHeaderParams(String key,double value)
        {
            return addHeaderParams(key,String.valueOf(value));
        }
        
        public HTTPHeaderInterceptor build()
        {
            return mHTTPHeaderInterceptor;
        }
    }
}
