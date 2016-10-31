package cn.kkserver.observer;

/**
 * Created by zhanghailong on 16/7/26.
 */
public interface IObject extends IGetter,ISetter {

    public java.lang.Object get(String[] keys);

    public void set(String[] keys, java.lang.Object value);

    public void remove(String[] keys);

    public String getString(String[] keys,String defaultValue);

    public int getInt(String[] keys, int defaultValue);

    public long getLong(String[] keys, long defaultValue);

    public float getFloat(String[] keys, float defaultValue);

    public double getDouble(String[] keys, double defaultValue);

    public boolean getBoolean(String[] keys, boolean defaultValue);
}
