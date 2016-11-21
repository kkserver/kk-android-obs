package cn.kkserver.observer;

/**
 * Created by zhanghailong on 2016/10/31.
 */
public interface IWithObserver extends IObserver {

    public String[] baseKeys();

    public void obtainObserver(IObserver observer,String[] baseKeys,java.lang.Object object);

    public void obtainObserver(IObserver observer,String[] baseKeys);

    public void recycle();

}
