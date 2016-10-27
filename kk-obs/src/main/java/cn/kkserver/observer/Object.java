package cn.kkserver.observer;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by zhanghailong on 16/7/26.
 */
public class Object implements IObject {

    public static String[] join(String[] baseKeys,String[] keys) {

        if(baseKeys == null && keys == null) {
            return null;
        }

        if(baseKeys == null) {
            return keys;
        }

        if(keys == null) {
            return baseKeys;
        }

        String[] vs = new String[baseKeys.length + keys.length];
        int i = 0;
        for(String v : baseKeys) {
            vs[i ++] = v;
        }
        for(String v : keys) {
            vs[i ++] = v;
        }
        return vs;
    }

    public static String[] keys(String key) {
        return key.split("\\.");
    }

    public static String[] slice(String[] keys,int begin,int end) {
        String[] v = new String[end - begin];
        int i = 0;
        while(begin < end && i< v.length) {
            v[i++] = keys[begin ++];
        }
        return v;
    }

    public static String[] slice(String[] keys,int begin) {
        String[] v = new String[keys.length - begin];
        int i = 0;
        while(begin < keys.length) {
            v[i++] = keys[begin ++];
        }
        return v;
    }

    public static java.lang.Object get(java.lang.Object v, String key) {

        if(v != null) {

            if(v instanceof Map) {
                Map<String, java.lang.Object> m = (Map<String, java.lang.Object>)v;
                if(m.containsKey(key)) {
                    v = m.get(key);
                }
                else {
                    v = null;
                }
            }
            else if(v instanceof List) {
                List<java.lang.Object> l = (List<java.lang.Object>) v;
                try {
                    int i = Integer.valueOf(key);
                    if(i >=0 && i < l.size()) {
                        v = l.get(i);
                    }
                    else {
                        v = null;
                    }
                }
                catch (Throwable e) {
                    v = null;
                }
            }
            else if(v instanceof IGetter) {
                v =((IGetter) v).get(key);
            }
            else {
                try {
                    Field fd = v.getClass().getField(key);
                    v = fd.get(v);
                } catch (Throwable e) {
                    v = null;
                }
            }

        }

        return v;
    }

    public static void set(java.lang.Object v , String key, java.lang.Object value) {

        if(v != null) {

            if(v instanceof Map) {
                ((Map<String, java.lang.Object>) v).put(key,value);
            }
            else if(v instanceof List) {
                List<java.lang.Object> l = (List<java.lang.Object>) v;
                try {
                    int i = Integer.valueOf(key);
                    if(i >=0 && i < l.size()) {
                        l.set(i,value);
                    }
                    else if(i == l.size()) {
                        l.add(value);
                    }
                }
                catch (Throwable e) {}
            }
            else if(v instanceof ISetter) {
                ((ISetter) v).set(key,value);
            }
            else {
                try {
                    Field fd = v.getClass().getField(key);
                    fd.set(v,value);
                } catch (Throwable e) {
                }
            }
        }
    }

    public static void remove(java.lang.Object v , String key) {

        if(v != null) {

            if(v instanceof Map) {
                ((Map<String, java.lang.Object>) v).remove(key);
            }
            else if(v instanceof List) {
                List<java.lang.Object> l = (List<java.lang.Object>) v;
                try {
                    int i = Integer.valueOf(key);
                    if(i >=0 && i < l.size()) {
                        l.remove(i);
                    }
                }
                catch (Throwable e) {}
            }
            else if(v instanceof ISetter) {
                ((ISetter) v).remove(key);
            }
            else {
                try {
                    Field fd = v.getClass().getField(key);
                    fd.set(v,null);
                } catch (Throwable e) {
                }
            }
        }
    }

    public static java.lang.Object get(java.lang.Object v, String[] keys) {

        int idx = 0;

        while(idx < keys.length) {

            String key = keys[idx];

            if(v != null) {

                v = get(v,key);

            }
            else {
                break;
            }

            idx ++ ;
        }

        return v;
    }

    public static void set(java.lang.Object v, String[] keys, java.lang.Object value) {

        int idx = 0;

        while(idx < keys.length) {

            String key = keys[idx];

            if(idx + 1 < keys.length) {
                java.lang.Object vv = get(v,key);
                if(vv == null) {
                    vv = new TreeMap<String, java.lang.Object>();
                    set(v,key,vv);
                }
                v = vv;
            }
            else {
                set(v,key,value);
                break;
            }

            idx ++;
        }

    }

    public static void remove(java.lang.Object v, String[] keys) {

        int idx = 0;

        while(v != null && idx < keys.length) {

            String key = keys[idx];

            if(idx + 1 < keys.length) {
                v = get(v,key);
            }
            else {
                remove(v,key);
                break;
            }

            idx ++;
        }
    }

    public java.lang.Object valueOf() {
        return this;
    }

    @Override
    public java.lang.Object get(String[] keys) {
        return get(valueOf(),keys);
    }

    @Override
    public void set(String[] keys, java.lang.Object value) {

        set(valueOf(),keys,value);

        onChangedKeys(keys);
    }

    @Override
    public void remove(String[] keys) {
        remove(valueOf(),keys);
        onChangedKeys(keys);
    }

    @Override
    public java.lang.Object get(String key) {
        return get(valueOf(),key);
    }

    @Override
    public void set(String key, java.lang.Object value) {
        set(valueOf(),key,value);
        onChangedKeys(new String[]{key});
    }

    @Override
    public void remove(String key) {
        remove(valueOf(),key);
        onChangedKeys(new String[]{key});
    }

    protected void onChangedKeys(String[] keys) {

    }
}