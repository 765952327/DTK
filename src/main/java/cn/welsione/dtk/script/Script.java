package cn.welsione.dtk.script;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import java.io.File;
import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;

@Slf4j
public class Script implements Serializable {
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
    
    public ScriptType getType() {
        return type;
    }
    
    public void setType(ScriptType type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
    
    public String getAbsolutePath() {
        return absolutePath;
    }
    
    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }
    
    
    public File getContext() {
        return context;
    }
    
    public void setContext(File context) {
        this.context = context;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String[] getArgs() {
        return args == null ? new String[]{} : args;
    }
    
    public void setArgs(String[] args) {
        this.args = args;
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
        if (res.length() == 0){
            return "";
        }
        return res.deleteCharAt(res.length() - 1).toString();
    }
    
    public String getLog() {
        String log = "";
        switch (type) {
            case TEMP:
                log = "[" + DateUtil.now() + "] " + FileUtil.readUtf8String(context) + " " + getCmd(args);
                break;
            case FILE:
                log = "[" + DateUtil.now() + "] sh " + context.getAbsolutePath() + " " + getCmd(args);
                break;
            default:
                break;
        }
        return log + "\n";
    }
}
