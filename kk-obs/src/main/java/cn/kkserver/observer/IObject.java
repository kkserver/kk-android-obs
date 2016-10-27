package cn.kkserver.observer;

/**
 * Created by zhanghailong on 16/7/26.
 */
public interface IObject extends IGetter,ISetter {

    public java.lang.Object get(String[] keys);

    public void set(String[] keys, java.lang.Object value);

    public void remove(String[] keys);

}
