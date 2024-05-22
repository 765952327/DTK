package cn.welsione.dtk.script.manager;

import cn.welsione.dtk.script.core.Script;

/**
 * 脚本管理器
 */
public interface ScriptManager {
    void create(Script script);
    
    Script getScriptByKey(String key);
}
