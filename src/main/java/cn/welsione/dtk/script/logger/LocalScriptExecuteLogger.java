package cn.welsione.dtk.script.logger;

import cn.welsione.dtk.framework.LocalPersister;
import cn.welsione.dtk.framework.Persister;
import cn.welsione.dtk.script.core.Script;
import cn.welsione.dtk.script.core.ScriptContext;
import cn.welsione.dtk.script.core.ScriptExecuteLogger;
import java.util.Date;

public class LocalScriptExecuteLogger implements ScriptExecuteLogger {
    private final static Persister<ScripLog> logger = new LocalPersister<>(ScripLog.class);
    @Override
    public void log(ScriptContext ctx) {
        Script script = ctx.getScript();
        
        ScripLog log = new ScripLog();
        log.setScriptId(script.getUuid());
        log.setExecutor(ctx.getExecutor().getClass().getSimpleName());
        log.setCreateTime(new Date());
        
        logger.create(log);
    }
    
    
}
