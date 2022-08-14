package cn.welsione.dtk.script;

public interface ScriptService {
    Script create(String key, String params, byte[] script, int type);
    
    Script findByKey(String key);
}
