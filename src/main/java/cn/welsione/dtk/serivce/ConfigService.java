package cn.welsione.dtk.serivce;

public interface ConfigService {
    void open();
    
    String getConfig(String key);
}
