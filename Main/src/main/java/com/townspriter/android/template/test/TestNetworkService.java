package com.townspriter.android.template.test;

import com.townspriter.network.AbstractServiceManager;
import com.townspriter.network.HTTPHeaderInterceptor;
import com.townspriter.network.IConfig;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/******************************************************************************
 * @path NetworkBase:TestNetworkService
 * @describe 网络请求测试类
 * @author 张飞
 * @email zhangfei@townspriter.com
 * @date 2021-11-23-20:33
 * CopyRight(C)2021 小镇精灵工作室版权所有
 * *****************************************************************************
 */
public class TestNetworkService extends AbstractServiceManager
{
    public TestNetworkService(@Nullable String baseUrl)
    {
        super(baseUrl);
    }
    
    @Override
    public boolean enableLogcat()
    {
        return true;
    }
    
    @Override
    public boolean processBaseConfig()
    {
        return false;
    }
    
    @NonNull
    @Override
    public okhttp3.OkHttpClient.Builder configHTTPClientBuilder()
    {
        return null;
    }
    
    @Nullable
    @Override
    public HTTPHeaderInterceptor.Builder configHTTPHeaderBuilder()
    {
        return null;
    }
    
    @NonNull
    @Override
    public IConfig configBaseConfigType()
    {
        return null;
    }
}
