package cn.kkserver.observer;

import android.os.Handler;
import android.os.Looper;

import java.lang.*;
import java.lang.Object;

/**
 * Created by zhanghailong on 2016/11/4.
 */

public class AsyncObserver extends Handler implements IObserver {

    private final IObserver _observer;

    public AsyncObserver(IObserver observer) {
        _observer = observer;
    }

    @Override
    public IObserver parent() {
        return _observer;
    }

    @Override
    public IObserver change(final String[] keys) {
        if(Looper.myLooper() == getLooper()) {
            _observer.change(keys);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.change(keys);
                }
            });
        }
        return this;
    }

    @Override
    public <T> IObserver on(final String[] keys, final Listener<T> listener, final T weakObject) {

        if(Looper.myLooper() == getLooper()) {
            _observer.on(keys,listener,weakObject);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.on(keys,listener,weakObject);
                }
            });
        }

        return this;
    }

    @Override
    public <T> IObserver off(final String[] keys,final Listener<T> listener,final T weakObject) {

        if(Looper.myLooper() == getLooper()) {
            _observer.off(keys,listener,weakObject);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.off(keys,listener,weakObject);
                }
            });
        }

        return this;
    }

    @Override
    public IWithObserver with(String[] baseKeys) {
        return new WithObserver(this,baseKeys);
    }

    @Override
    public IWithObserver with(String[] baseKeys, Object object) {
        return new WithObserver(this,baseKeys,object);
    }

    @Override
    public Object get(String[] keys) {
        return _observer.get(keys);
    }

    @Override
    public void set(final String[] keys,final Object value) {
        if(Looper.myLooper() == getLooper()) {
            _observer.set(keys,value);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.set(keys,value);
                }
            });
        }
    }

    @Override
    public void remove(final String[] keys) {
        if(Looper.myLooper() == getLooper()) {
            _observer.remove(keys);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.remove(keys);
                }
            });
        }
    }

    @Override
    public String getString(String[] keys, String defaultValue) {
        return _observer.getString(keys,defaultValue);
    }

    @Override
    public int getInt(String[] keys, int defaultValue) {
        return _observer.getInt(keys,defaultValue);
    }

    @Override
    public long getLong(String[] keys, long defaultValue) {
        return _observer.getLong(keys,defaultValue);
    }

    @Override
    public float getFloat(String[] keys, float defaultValue) {
        return _observer.getFloat(keys,defaultValue);
    }

    @Override
    public double getDouble(String[] keys, double defaultValue) {
        return _observer.getDouble(keys,defaultValue);
    }

    @Override
    public boolean getBoolean(String[] keys, boolean defaultValue) {
        return _observer.getBoolean(keys,defaultValue);
    }

    @Override
    public Object get(String key) {
        return _observer.get(key);
    }

    @Override
    public void set(final String key, final Object value) {
        if(Looper.myLooper() == getLooper()) {
            _observer.set(key,value);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.set(key,value);
                }
            });
        }
    }

    @Override
    public void remove(final String key) {
        if(Looper.myLooper() == getLooper()) {
            _observer.remove(key);
        }
        else {
            post(new Runnable() {
                @Override
                public void run() {
                    _observer.remove(key);
                }
            });
        }
    }
}
