package cn.kkserver.observer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhanghailong on 16/7/13.
 */
public class Observer extends Object implements IObserver {

    private KeyObserver _keyObserver = new KeyObserver();

    private java.lang.Object _object;

    @Override
    public java.lang.Object valueOf() {
        if(_object == null) {
            _object = new TreeMap<String,java.lang.Object>();
        }
        return _object;
    }

    public void setValue(java.lang.Object object) {
        _object = object;
        onChangedKeys(null);
    }

    @Override
    protected void onChangedKeys(String[] keys) {

        int idx = 0;
        KeyObserver obs = _keyObserver;

        while(keys != null && idx < keys.length) {

            String key = keys[idx];

            obs = obs.keyObserver(key);

            idx ++;

            if (idx == keys.length) {
                obs.change(this,keys);
                return;
            }
        }

        obs.change(this,keys);
    }

    @Override
    public IObserver change(String[] keys) {

        onChangedKeys(keys);

        return this;
    }

    @Override
    public <T extends java.lang.Object> IObserver on(String[] keys,Listener<T> listener,T weakObject) {

        KeyListener<T> keyListener = new KeyListener<>(listener,weakObject);

        int idx = 0;
        KeyObserver obs = _keyObserver;

        while(keys != null && idx < keys.length) {

            String key = keys[idx];

            obs = obs.keyObserver(key);

            idx ++;

            if (idx == keys.length) {
                obs.on(keyListener);
                return this;
            }

        }

        _keyObserver.on(keyListener);


        return this;
    }

    @Override
    public <T extends java.lang.Object> IObserver off(String[] keys,Listener<T> listener,T weakObject) {

        if(keys == null && listener == null && weakObject == null) {
            _keyObserver = new KeyObserver();
            return this;
        }

        if(keys == null){
            _keyObserver.off(listener,weakObject,true);
            return this;
        }

        int idx = 0;
        KeyObserver obs = _keyObserver;

        while(idx < keys.length) {

            String key = keys[idx];

            obs = obs.keyObserver(key);

            idx ++;

            if (idx == keys.length) {
                obs.off(listener,weakObject,false);
            }
        }

        return this;
    }

    private static class KeyListener<T extends java.lang.Object> {

        public final Listener<T> listener;
        private final WeakReference<T> _weakObject;

        public KeyListener(Listener<T> listener,T weakObject) {
            this.listener = listener;
            _weakObject = new WeakReference<T>(weakObject);
        }

        public T weakObject() {
            return _weakObject.get();
        }
    }

    private static class KeyObserver {

        private final List<KeyListener<java.lang.Object>> _listeners = new LinkedList<>();
        private final Map<String,KeyObserver> _observers = new TreeMap<String,KeyObserver>();

        public void on(KeyListener listener) {
            _listeners.add(listener);
        }

        public <T extends java.lang.Object> void off(Listener<T> listener,T weakObject,boolean children) {

            if(listener == null && weakObject == null) {
                _listeners.clear();
                if(children) {
                    _observers.clear();
                }
                return;
            }

            Iterator<KeyListener<java.lang.Object>> i = _listeners.iterator();

            while(i.hasNext()) {
                KeyListener<?> keyListener = i.next();
                if((listener == null || listener == keyListener.listener)
                        && (weakObject == null || weakObject == keyListener.weakObject())) {
                    i.remove();
                }
            }

            if(children) {
                for(KeyObserver v : _observers.values()) {
                    v.off(listener,weakObject,children);
                }
            }
        }

        public void change(Observer observer,String[] keys) {

            List<KeyListener<java.lang.Object>> ls = new ArrayList<>(_listeners);

            for(KeyListener<java.lang.Object> v : ls) {
                v.listener.onChanged(observer,keys, v.weakObject());
            }

            for(KeyObserver children : _observers.values()) {
                children.change(observer,keys);
            }
        }

        public KeyObserver keyObserver(String key) {
            if(_observers.containsKey(key)) {
                return _observers.get(key);
            }
            KeyObserver v = new KeyObserver();
            _observers.put(key,v);
            return v;
        }

    }

    @Override
    public IWithObserver with(String[] baseKeys) {
        return new WithObserver(this,baseKeys);
    }

    @Override
    public IWithObserver with(String[] baseKeys,java.lang.Object object) {
        return new WithObserver(this,baseKeys,object);
    }

}

