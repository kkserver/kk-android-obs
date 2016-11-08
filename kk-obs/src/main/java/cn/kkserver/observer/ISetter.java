package cn.kkserver.observer;

/**
 * Created by zhanghailong on 16/7/13.
 */
public interface ISetter extends cn.kkserver.core.ISetter {

    public void set(String key, java.lang.Object value);

    public void remove(String key);

}
