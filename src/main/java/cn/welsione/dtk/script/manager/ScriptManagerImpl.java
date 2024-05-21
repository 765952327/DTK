package cn.welsione.dtk.script.manager;

import cn.welsione.dtk.framework.LocalPersister;
import cn.welsione.dtk.framework.Persister;
import cn.welsione.dtk.script.core.Script;

public class ScriptManagerImpl implements ScriptManager {
    private Persister<Script> persister = new LocalPersister<>(Script.class);
    
    
}
