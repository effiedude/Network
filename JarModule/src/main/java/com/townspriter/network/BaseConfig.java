package com.townspriter.network;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import androidx.annotation.Nullable;

/******************************************************************************
 * @path RxNetwork:BaseConfig
 * @version 1.0.0.0
 * @describe
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:51:39
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class BaseConfig implements BaseJsonConstant,IConfig
{
    /**
     * 10000:操作成功
     * 10001:操作失败
     * 10002:参数检验失败
     * 10003:暂未登录或验签已经过期
     * 10004:没有相关权限
     */
    @SerializedName(CODE)
    public int code;
    @SerializedName(MESSAGE)
    public String message;
    @Nullable
    @SerializedName(DATA)
    public Object data;
    
    @Override
    public IConfig parse(String content)
    {
        Gson gson=new Gson();
        return gson.fromJson(content,BaseConfig.class);
    }
    
    @Override
    public boolean isSuccessful()
    {
        return code==SERVERxREQUESTxCODEx10000;
    }
    
    @Override
    public boolean isFull()
    {
        return null!=data;
    }
    
    @Override
    public int getCode()
    {
        return code;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
    
    @Override
    public String getDataKey()
    {
        return DATA;
    }
    
    @Override
    public String toString()
    {
        return "\n{"+"\n\tcode:"+code+"\n\tmessage:"+message+"\n\tdata:"+(null!=data?data.toString():"data:NULL")+"\n"+'}';
    }
}
