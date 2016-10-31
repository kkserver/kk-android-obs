package cn.kkserver.observer;

/**
 * Created by zhanghailong on 2016/10/31.
 */
public interface IWithObserver extends IObserver {

    public IObserver observer();

    public String[] baseKeys();

    public void recycle();

}
