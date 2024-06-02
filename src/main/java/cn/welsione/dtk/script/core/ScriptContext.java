package cn.welsione.dtk.script.core;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.core.executor.ShellScriptExecutor;
import java.io.File;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;

@Slf4j
@Data
public class ScriptContext implements Serializable {
    private ScriptExecutor executor = new ShellScriptExecutor();
    
    private Script script;
    
    private ScriptType type;
    /**
     * 唯一KEY
     */
    private String key;
    /**
     * 相对路径
     */
    private String path;
    /**
     * 绝对路径
     */
    private String absolutePath;
    /**
     * 脚本内容
     */
    private File context;
    /**
     * 脚本名称
     */
    private String name;
    /**
     * 脚本参数
     */
    private String[] args;
    
    
    public String[] getArgs() {
        return args == null ? new String[]{} : args;
    }
    
    public void close() {
        if (type == ScriptType.TEMP) {
            log.info("{} script closed, delete temp script {}", Marker.ANY_MARKER, context.getAbsolutePath());
            context.deleteOnExit();
        }
    }
    
    private static String getCmd(String... command) {
        StringBuilder res = new StringBuilder();
        for (String cmd : command) {
            res.append(cmd).append(" ");
        }
        if (res.length() == 0) {
            return "";
        }
        return res.deleteCharAt(res.length() - 1).toString();
    }
    
    public Script getScript() {
        Script script = new Script();
        script.setKey(key);
        script.setPath(path);
        script.setName(name);
        script.setType(type);
        if (type == ScriptType.TEMP) {
            script.setContent(FileUtil.readUtf8String(context));
        }
        return script;
    }
    

    
    public void execute() {
        executor.execute(this);
    }
}
