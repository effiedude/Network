package com.townspriter.android.base.network;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.internal.LinkedTreeMap;

/******************************************************************************
 * @path RxNetwork:MapDeserializerDoubleToInt
 * @version 1.0.0.0
 * @describe 双精度转义整形.服务端下发的数据在解析时被转义修改了类型导致请求接口参数类型错误出现异常
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:45:42
 * *****************************************************************************
 */
public class MapDeserializerDoubleToInt implements JsonDeserializer<Map<String,Object>>
{
    private static final String TAG="MapDeserializerDoubleToInt";
    
    @Override
    @SuppressWarnings("unchecked")
    public Map<String,Object> deserialize(JsonElement json,Type typeOfT,JsonDeserializationContext context) throws JsonParseException
    {
        return (Map<String,Object>)read(json);
    }
    
    public Object read(JsonElement jsonElement)
    {
        if(jsonElement.isJsonArray())
        {
            List<Object> list=new ArrayList<>();
            JsonArray arr=jsonElement.getAsJsonArray();
            for(JsonElement anArr:arr)
            {
                list.add(read(anArr));
            }
            return list;
        }
        else if(jsonElement.isJsonObject())
        {
            Map<String,Object> map=new LinkedTreeMap<>();
            JsonObject obj=jsonElement.getAsJsonObject();
            Set<Map.Entry<String,JsonElement>> entitySet=obj.entrySet();
            for(Map.Entry<String,JsonElement> entry:entitySet)
            {
                map.put(entry.getKey(),read(entry.getValue()));
            }
            return map;
        }
        else if(jsonElement.isJsonPrimitive())
        {
            JsonPrimitive jsonPrimitive=jsonElement.getAsJsonPrimitive();
            if(jsonPrimitive.isBoolean())
            {
                return jsonPrimitive.getAsBoolean();
            }
            else if(jsonPrimitive.isString())
            {
                return jsonPrimitive.getAsString();
            }
            else if(jsonPrimitive.isNumber())
            {
                Number number=jsonPrimitive.getAsNumber();
                if(Math.ceil(number.doubleValue())==number.longValue())
                {
                    return number.longValue();
                }
                else
                {
                    return number.doubleValue();
                }
            }
        }
        return null;
    }
}
