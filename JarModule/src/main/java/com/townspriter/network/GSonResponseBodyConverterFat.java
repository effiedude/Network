package com.townspriter.network;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.Nullable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import androidx.annotation.NonNull;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/******************************************************************************
 * @path RxNetwork:GSonResponseBodyConverterFat
 * @version 1.0.0.0
 * @describe 解析工厂数据接收插件
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:51:39
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class GSonResponseBodyConverterFat<T> implements Converter<ResponseBody,T>,BaseJsonConstant
{
    private static final String TAG="GSonResponseBodyConverterFat";
    private final Gson mGSon;
    private final Type mType;
    private IConfig mConfig;
    private String mUrl;
    
    public GSonResponseBodyConverterFat(@NonNull Gson gson,@NonNull Type type,@Nullable IConfig config,@Nullable String url)
    {
        mGSon=gson;
        mType=type;
        mConfig=config;
        mUrl=url;
    }
    
    @Nullable
    @Override
    public T convert(ResponseBody value) throws IOException
    {
        try
        {
            String response=value.string();
            // 注意:带有范型内容的数据在转成字符串之后整形会变成双精度类型.通过哈希表规避此问题
            Map<String,Object> configMap=mGSon.fromJson(response,new TypeToken<Map<String,Object>>()
            {}.getType());
            mConfig=mConfig.parse(response);
            if(mConfig.isSuccessful())
            {
                return mGSon.fromJson(mGSon.toJson(configMap.get(mConfig.getDataKey())),mType);
            }
            else
            {
                BaseException baseException=new BaseException(mConfig.getCode(),mConfig.getMessage(),mUrl);
                EventBus.getDefault().post(baseException);
            }
        }
        catch(Exception exception)
        {
            BaseException baseException=new BaseException(exception,mUrl);
            EventBus.getDefault().post(baseException);
        }
        finally
        {
            value.close();
        }
        return null;
    }
}
