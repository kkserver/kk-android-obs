package cn.kkserver.observer;

import java.lang.*;

/**
 * Created by zhanghailong on 16/7/13.
 */
public interface Listener<T extends java.lang.Object>{

    public void onChanged(IObserver observer, String[] changedKeys, T weakObject);

}
