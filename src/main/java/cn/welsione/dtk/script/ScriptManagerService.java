package cn.welsione.dtk.script;

public interface ScriptManagerService {
    Script add(String key,String params,String path);
    
    boolean execute(String key,String... command);
}
