package cn.welsione.dtk.script.core.logger;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.core.ScriptContext;
import cn.welsione.dtk.script.core.ScriptExecuteLogger;
import java.io.File;

public class LocalScriptExecuteLogger implements ScriptExecuteLogger {
    @Override
    public void log(ScriptContext script) {
        File file = FileUtil.file("./log/execute.log");
        FileUtil.appendUtf8String(script.getLog(), file);
    }
}
