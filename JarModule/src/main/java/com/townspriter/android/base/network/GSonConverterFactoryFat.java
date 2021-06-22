package com.townspriter.android.base.network;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;

/******************************************************************************
 * @path RxNetwork:GSonConverterFactoryFat
 * @version 1.0.0.0
 * @describe 数据解析工厂.对服务端数据进行拦截解析
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:45:42
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class GSonConverterFactoryFat extends Converter.Factory
{
    private static final String TAG="GSonConverterFactoryFat";
    private final Gson mGSon;
    private IConfig mConfig;
    
    private GSonConverterFactoryFat(@NonNull Gson gson,@Nullable IConfig config)
    {
        mGSon=gson;
        mConfig=config;
    }
    
    public static GSonConverterFactoryFat create(@Nullable IConfig config)
    {
        return new GSonConverterFactoryFat(buildGSon(),config);
    }
    
    private static Gson buildGSon()
    {
        GsonBuilder gsonBuilder=new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<Map<String,Object>>()
        {}.getType(),new MapDeserializerDoubleToInt());
        return gsonBuilder.create();
    }
    
    @Nullable
    @Override
    public Converter<?,RequestBody> requestBodyConverter(Type type,Annotation[] parameterAnnotations,Annotation[] methodAnnotations,Retrofit retrofit)
    {
        TypeAdapter<?> adapter=mGSon.getAdapter(TypeToken.get(type));
        return new GSonRequestBodyConverterFat<>(mGSon,adapter);
    }
    
    @Nullable
    @Override
    public Converter<ResponseBody,?> responseBodyConverter(Type type,Annotation[] annotations,Retrofit retrofit)
    {
        return new GSonResponseBodyConverterFat<>(mGSon,type,mConfig,getUrl(annotations));
    }
    
    private @Nullable String getUrl(@Nullable Annotation[] annotations)
    {
        if(annotations!=null&&annotations.length>0)
        {
            for(int i=0;i<annotations.length;i++)
            {
                boolean isGetClassType=annotations[i].annotationType()==GET.class;
                if(isGetClassType)
                {
                    GET get=(GET)annotations[i];
                    return get.value();
                }
                boolean isPostClassType=annotations[i].annotationType()==POST.class;
                if(isPostClassType)
                {
                    POST post=(POST)annotations[i];
                    return post.value();
                }
            }
        }
        return null;
    }
}
