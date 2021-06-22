package com.townspriter.android.base.network;
/******************************************************************************
 * @path RxNetwork:BaseJsonConstant
 * @version 1.0.0.0
 * @describe 基础请求数据字段
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 14:16:10
 * CopyRight(C)2021 智慧培森科技版权所有
 * *****************************************************************************
 */
public interface BaseJsonConstant
{
    /**
     * 服务端错误码
     * 10000:操作成功
     * 10001:操作失败
     * 10002:参数检验失败
     * 10003:暂未登录或验签已经过期
     * 10004:没有相关权限
     */
    int SERVERxREQUESTxCODEx10000=10000;
    int SERVERxREQUESTxCODEx10001=10001;
    String CODE="errno";
    String MESSAGE="errmsg";
    String DATA="data";
}
