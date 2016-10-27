package cn.kkserver.observer;

import java.lang.Object;

/**
 * Created by zhanghailong on 16/7/26.
 */
public interface IObserver extends IObject {

    public IObserver change(String[] keys);

    public <T extends java.lang.Object> IObserver on(String[] keys,Listener<T> listener,T weakObject);

    public <T extends java.lang.Object> IObserver off( String[] keys,Listener<T> listener,T weakObject);

    public IObserver with(String[] baseKeys);

    public IObserver with(String[] baseKeys,java.lang.Object object);

}
