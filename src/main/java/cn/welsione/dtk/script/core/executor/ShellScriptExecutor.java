package cn.welsione.dtk.script.core.executor;

import cn.welsione.dtk.script.core.ScriptContext;
import cn.welsione.dtk.script.core.ScriptExecuteLogger;
import cn.welsione.dtk.script.core.ScriptExecutor;
import cn.welsione.dtk.script.logger.LocalScriptExecuteLogger;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

/**
 * 目前只支持SHELL脚本
 */
@Slf4j
public class ShellScriptExecutor implements ScriptExecutor {
    private static final ScriptExecuteLogger executeLogger = new LocalScriptExecuteLogger();
    
    @Override
    public void execute(ScriptContext script) {
        File file = script.getContext();
        List<String> cmd = new ArrayList<>();
        cmd.add("sh");
        cmd.add(file.getAbsolutePath());
        cmd.addAll(Arrays.asList(script.getArgs()));
        ShellUtil.execInherit(cmd.toArray(new String[0]));
        executeLogger.log(script);
        script.close();
    }
    
    
}
