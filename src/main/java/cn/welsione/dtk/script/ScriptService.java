package cn.welsione.dtk.script;

public interface ScriptService {
    Script create(String key, String params, String path, int type);
    
    Script findByKey(String key);
}
