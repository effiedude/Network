package com.townspriter.network;

/******************************************************************************
 * @path RxNetwork:ObserverLoader
 * @version 1.0.0.0
 * @describe
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-31 11:19:23
 * *****************************************************************************
 */
import androidx.annotation.NonNull;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ObserverLoader
{
    public static <T> Observable<T> observe(@NonNull Observable<T> observable)
    {
        return observable.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
