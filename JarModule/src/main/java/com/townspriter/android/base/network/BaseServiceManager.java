package com.townspriter.android.base.network;

import java.util.Map;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;

/******************************************************************************
 * @path RxNetwork:BaseServiceManager
 * @version 1.0.0.0
 * @describe 数据请求
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 11:39:21
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class BaseServiceManager extends AbstractServiceManager
{
    private final static String TAG="BaseServiceManager";
    
    public BaseServiceManager(String baseUrl)
    {
        super(baseUrl);
    }
    
    @Override
    public void onInterceptHeaderParams(Map<String,Object> headerParamsMap)
    {
        super.onInterceptHeaderParams(headerParamsMap);
        Log.d(TAG,"updateHeaderParams");
    }
    
    @Override
    public boolean enableLogcat()
    {
        return false;
    }
    
    @Override
    public boolean processBaseConfig()
    {
        return false;
    }
    
    @Override
    @NonNull
    public OkHttpClient.Builder configHTTPClientBuilder()
    {
        return null;
    }
    
    @Override
    @Nullable
    public HTTPHeaderInterceptor.Builder configHTTPHeaderBuilder()
    {
        return null;
    }
    
    @Nullable
    @Override
    public IConfig configBaseConfigType()
    {
        return new BaseConfig();
    }
}
