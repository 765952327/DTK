package cn.welsione.dtk.framework;

import java.util.List;

public interface Persister<T> {
    void create(T t);
    
    void update(T t);
    
    List<T> queryAll();
    
    List<T> query(T condition);
    
    void delete(T t);
    
    T get(String uuid);
    
    T selectOne(T condition);
}
