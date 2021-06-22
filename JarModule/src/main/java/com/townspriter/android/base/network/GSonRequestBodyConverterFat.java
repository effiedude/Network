package com.townspriter.android.base.network;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.Nullable;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonWriter;
import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/******************************************************************************
 * @path RxNetwork:GSonRequestBodyConverterFat
 * @version 1.0.0.0
 * @describe 解析工厂数据请求插件
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:51:39
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class GSonRequestBodyConverterFat<T> implements Converter<T,RequestBody>
{
    private static final String TAG="GSonRequestBodyConverterFat";
    private static final MediaType MEDIAxTYPE=MediaType.get("application/json; charset=UTF-8");
    private static final Charset UTFx8=StandardCharsets.UTF_8;
    private final Gson mGSon;
    private final TypeAdapter<T> mTypeAdapter;
    
    public GSonRequestBodyConverterFat(@NonNull Gson gson,@NonNull TypeAdapter<T> typeAdapter)
    {
        mGSon=gson;
        mTypeAdapter=typeAdapter;
    }
    
    @Nullable
    @Override
    public RequestBody convert(T value) throws IOException
    {
        Buffer buffer=new Buffer();
        Writer writer=new OutputStreamWriter(buffer.outputStream(),UTFx8);
        JsonWriter jsonWriter=mGSon.newJsonWriter(writer);
        mTypeAdapter.write(jsonWriter,value);
        jsonWriter.close();
        return RequestBody.create(MEDIAxTYPE,buffer.readByteString());
    }
}
