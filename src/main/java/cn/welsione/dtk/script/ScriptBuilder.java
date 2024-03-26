package cn.welsione.dtk.script;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import java.io.File;
import java.nio.file.Files;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ScriptBuilder {
    private ScriptType type;
    private String key;
    private String path;
    private String absolutePath;
    private File context;
    private String name;
    private String[] params;
    private String[] args;
    
    private ScriptBuilder() {
    }
    
    public static ScriptBuilder builder() {
        return new ScriptBuilder();
    }
    
    
    public ScriptBuilder type(ScriptType type) {
        this.type = type;
        return this;
    }
    
    public ScriptBuilder key(String key) {
        this.key = key;
        return this;
    }
    
    public ScriptBuilder path(String path) {
        this.path = path;
        return this;
    }
    
    public ScriptBuilder absolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
        return this;
    }
    
    public ScriptBuilder context(File context) {
        this.context = context;
        return this;
    }
    
    public ScriptBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public ScriptBuilder params(String... params) {
        this.params = params;
        return this;
    }
    
    public ScriptBuilder args(String... args) {
        this.args = args;
        return this;
    }
    
    public ScriptBuilder shell(File file){
        if (file == null){
            throw new IllegalArgumentException("shell file is null");
        }
        this.absolutePath = file.getAbsolutePath();
        this.path = file.getPath();
        this.name = file.getName();
        this.type = ScriptType.FILE;
        this.context = file;
        try {
            Stream<String> lines = Files.lines(file.toPath());
            lines.forEach(line ->{
                if (line.startsWith("#!params ")) {
                    this.params = line.replace("#!params ", "").split(" ");
                }
                if (line.startsWith("#!key ")) {
                    this.key = line.replace("#!key ", "");
                }
            });
        } catch (Exception e){
            log.error("read shell is null");
        }
        if (StrUtil.isBlank(key)){
            throw new IllegalArgumentException("current shell not has '#!key '");
        }
        return this;
    }
    
    public ScriptBuilder temp(String key, String context, String[] params) {
        this.type = ScriptType.TEMP;
        this.name = "temp-" + UUID.randomUUID() + "-" + key;
        this.key = this.name;
        File file = FileUtil.touch("./temp/" + name + ".sh");
        this.context = FileUtil.writeBytes(context.getBytes(), file);
        this.absolutePath = file.getAbsolutePath();
        this.path = file.getPath();
        this.params = params;
        return this;
    }
    
    public Script build() {
        Script script = new Script();
        script.setType(type);
        script.setKey(key);
        script.setPath(path);
        script.setAbsolutePath(absolutePath);
        script.setContext(context);
        script.setName(name);
        script.setParams(params);
        script.setArgs(args);
        return script;
    }
    
    
}
