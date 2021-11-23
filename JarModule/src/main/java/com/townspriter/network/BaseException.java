package com.townspriter.network;

import androidx.annotation.Nullable;

/******************************************************************************
 * @path RxNetwork:BaseException
 * @version 1.0.0.0
 * @describe 公共请求错误码过滤器
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 19:51:39
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public class BaseException extends Throwable
{
    private final int code;
    private @Nullable final String message;
    private @Nullable String url;
    
    public BaseException(int code,@Nullable String message,@Nullable String url)
    {
        this.code=code;
        this.message=message;
        this.url=url;
    }
    
    public BaseException(Throwable throwable,@Nullable String url)
    {
        this(0,"内部异常",url);
    }
    
    public int getCode()
    {
        return code;
    }
    
    @Nullable
    @Override
    public String getMessage()
    {
        return message;
    }
    
    @Nullable
    public String getUrl()
    {
        return url;
    }
    
    @Override
    public String toString()
    {
        return "\nBaseException\n{"+"\n\tcode:"+code+"\n\tmessage:"+message+"\n\turl:"+url+"\n}";
    }
}
