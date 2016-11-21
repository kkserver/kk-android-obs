package cn.kkserver.observer;


import cn.kkserver.core.Value;

/**
 * Created by zhanghailong on 2016/10/27.
 */
public class WithObserver extends Observer implements IWithObserver{

    private final static Listener<WithObserver> _L = new Listener<WithObserver>() {

        @Override
        public void onChanged(IObserver observer, String[] keys, WithObserver weakObject) {

            if(weakObject != null && weakObject._observer == observer) {
                if (keys == null || keys.length == 0 || keys.length <= weakObject._baseKeys.length) {
                    weakObject.setValue(null);
                } else {
                    weakObject.change(Object.slice(keys, weakObject._baseKeys.length));
                }
            }
        }
    };

    private String[] _baseKeys;
    private IObserver _observer;

    @Override
    public IObserver parent() {
        return _observer;
    }

    @Override
    public String[] baseKeys() {
        return _baseKeys;
    }

    @Override
    public void obtainObserver(IObserver observer, String[] baseKeys, java.lang.Object object) {
        if((observer != this)
                && baseKeys != null
                && ( _observer != observer || !Value.equals(_baseKeys,baseKeys))) {
            if(_observer != null) {
                _observer.off(_baseKeys,_L,this);
            }
            _observer = observer;
            _baseKeys = baseKeys;
            setValue(object);
            observer.on(_baseKeys,_L,this);
        }
    }

    @Override
    public void obtainObserver(IObserver observer, String[] baseKeys) {
        obtainObserver(observer,baseKeys,null);
    }

    @Override
    public java.lang.Object valueOf() {
        if(_object == null) {
            _object = _observer.get(_baseKeys);
        }
        return _object;
    }

    @Override
    public void recycle(){

        if(_observer != null) {
            _observer.off(_baseKeys,_L,this);
            _observer = null;
            _baseKeys = null;
        }
    }

    @Override
    protected void finalize() throws Throwable {

        if(_observer != null) {
            _observer.off(_baseKeys,_L,this);
        }

        super.finalize();
    }

}
