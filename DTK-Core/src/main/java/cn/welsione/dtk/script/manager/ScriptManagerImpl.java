package cn.welsione.dtk.script.manager;

import cn.welsione.dtk.framework.LocalPersister;
import cn.welsione.dtk.framework.Persister;
import cn.welsione.dtk.script.core.Script;

public class ScriptManagerImpl implements ScriptManager {
    private final Persister<Script> persister = new LocalPersister<>(Script.class);
    
    
    @Override
    public void create(Script script) {
        persister.create(script);
    }
    
    @Override
    public Script getScriptByKey(String key) {
        Script condition = new Script();
        condition.setKey(key);
        return persister.selectOne(condition);
    }
    
    
}
