package cn.welsione.dtk.config;

public interface ConfigService {
    void open();
    
    <T> T getConfig(String key,Class<T> clazz);
    
    boolean setConfig(String key,String val);
}
