package com.townspriter.network;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/******************************************************************************
 * @path Notes:IConfig
 * @version 1.0.0.0
 * @describe 头信息解析类规则
 * @author 张飞
 * @email zhangfei@townspriter.com
 * @date 2021-06-03 22:33:09
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public interface IConfig
{
    /**
     * parse
     * 解析头信息
     * 
     * @param content
     * 服务端数据源
     */
    IConfig parse(@Nullable String content);
    
    /**
     * isSuccessful
     * 是否请求成功.根据协议错误码判断
     * 
     * @return true:请求成功 false:请求失败
     */
    boolean isSuccessful();
    
    /**
     * isFull
     * 是否包含实际数据
     * 
     * @return true:是 false:否
     */
    boolean isFull();
    
    /**
     * getCode
     * 返回错误码
     * 
     * @return 具体错误码
     */
    int getCode();
    
    /**
     * getMessage
     * 返回错误信息
     *
     * @return 具体错误信息
     */
    @Nullable
    String getMessage();
    
    /**
     * getDataKey
     * 获取内容数据对应的键值对
     * 
     * @return 键值对
     */
    @NonNull
    String getDataKey();
}
