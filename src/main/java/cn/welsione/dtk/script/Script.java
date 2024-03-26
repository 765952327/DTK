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
     * 脚本字段
     */
    private String[] params;
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
    
    public String[] getParams() {
        return params == null ? new String[]{} : params;
    }
    
    public void setParams(String[] params) {
        this.params = params;
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
    
    public String[] getFinalParams(){
        return interleaveArrays(params,args);
    }
    
    private String[] interleaveArrays(String[] arr1, String[] arr2) {
        if (arr1 == null || arr1.length == 0) {
            return arr2;
        }
        
        if (arr2 == null || arr2.length == 0) {
            return arr1;
        }
        
        int totalLength = arr1.length + arr2.length;
        String[] result = new String[totalLength];
        
        int index = 0;
        int i = 0;
        int j = 0;
        
        while (i < arr1.length && j < arr2.length) {
            result[index++] = arr1[i++];
            result[index++] = arr2[j++];
        }
        
        while (i < arr1.length) {
            result[index++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            result[index++] = arr2[j++];
        }
        
        return result;
    }
}
