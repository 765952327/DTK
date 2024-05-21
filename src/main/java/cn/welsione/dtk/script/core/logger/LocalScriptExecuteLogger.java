package cn.welsione.dtk.script.core.logger;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.core.Script;
import cn.welsione.dtk.script.core.ScriptExecuteLogger;
import java.io.File;

public class LocalScriptExecuteLogger implements ScriptExecuteLogger {
    @Override
    public void log(Script script) {
        File file = FileUtil.file("./log/execute.log");
        FileUtil.appendUtf8String(script.getLog(), file);
    }
}
