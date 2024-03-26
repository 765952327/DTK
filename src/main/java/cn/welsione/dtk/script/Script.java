package cn.welsione.dtk.script;

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
    
}
