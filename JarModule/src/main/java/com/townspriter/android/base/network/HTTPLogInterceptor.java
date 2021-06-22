package com.townspriter.android.base.network;

import org.jetbrains.annotations.NotNull;
import android.util.Log;
import okhttp3.logging.HttpLoggingInterceptor;

/******************************************************************************
 * @path RxNetwork:HTTPLogInterceptor
 * @version 1.0.0.0
 * @describe 网络请求日志截取器
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-29 16:49:00
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class HTTPLogInterceptor implements HttpLoggingInterceptor.Logger
{
    private static final String TAG="HTTPLogInterceptor";
    private boolean enableLogcat;
    
    public HTTPLogInterceptor(boolean enableLogcat)
    {
        this.enableLogcat=enableLogcat;
    }
    
    @Override
    public void log(@NotNull String log)
    {
        if(enableLogcat)
        {
            Log.d(TAG,"HTTP:"+log);
        }
    }
}
