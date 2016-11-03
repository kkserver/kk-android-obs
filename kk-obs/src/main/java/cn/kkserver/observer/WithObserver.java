package cn.kkserver.observer;

import java.util.TreeMap;

/**
 * Created by zhanghailong on 2016/10/27.
 */
public class WithObserver extends Observer implements IWithObserver{

    private final String[] _baseKeys;
    private final Observer _observer;

    @Override
    public IObserver observer() {
        return _observer;
    }

    @Override
    public String[] baseKeys() {
        return _baseKeys;
    }

    public WithObserver(Observer observer,String[] baseKeys,java.lang.Object object) {
        this(observer,baseKeys);
        setValue(object);
    }

    @Override
    public java.lang.Object valueOf() {
        if(_object == null) {
            _object = _observer.get(_baseKeys);
        }
        return _object;
    }

    public WithObserver(Observer observer, final String[] baseKeys) {
        _baseKeys = baseKeys;
        _observer = observer;
        _observer.on(baseKeys, new Listener<WithObserver>() {

            @Override
            public void onChanged(IObserver observer, String[] keys, WithObserver weakObject) {

                if (keys == null || keys.length == 0 || keys.length <= baseKeys.length) {
                    weakObject.setValue(null);
                } else {
                    weakObject.change(Object.slice(keys, baseKeys.length));
                }

            }

        },this);

    }

    @Override
    public void recycle(){
        _observer.off(_baseKeys,null,this);
    }

    @Override
    protected void finalize() throws Throwable {

        _observer.off(_baseKeys,null,this);

        super.finalize();
    }
}
