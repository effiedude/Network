package com.townspriter.network;

import java.util.concurrent.atomic.AtomicBoolean;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/******************************************************************************
 * @path SuperScholar:SingleLiveData
 * @version 1.0.0.0
 * @describe 限制数据更新后只能通知一次
 * @author 张飞
 * @email zhangfei@personedu.com
 * @date 2021-05-30 17:15:58
 * CopyRight(C)2021 智慧陪森科技版权所有
 * *****************************************************************************
 */
public class SingleLiveData<T>extends MutableLiveData<T>
{
    private static final String TAG="SingleLiveData";
    private final AtomicBoolean mLock=new AtomicBoolean();
    
    @Override
    public void observe(@NonNull LifecycleOwner owner,@NonNull Observer<? super T> observer)
    {
        if(hasActiveObservers())
        {
            Log.d(TAG,"observe:hasActiveObservers");
        }
        super.observe(owner,t->
        {
            if(mLock.compareAndSet(true,false))
            {
                observer.onChanged(t);
            }
        });
    }
    
    @Override
    public void setValue(T value)
    {
        mLock.set(true);
        super.setValue(value);
    }
    
    @Override
    public void postValue(T value)
    {
        mLock.set(true);
        super.postValue(value);
    }
}
