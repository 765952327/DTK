package cn.welsione.dtk.script.core;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.welsione.dtk.script.manager.ScriptManager;
import cn.welsione.dtk.script.manager.ScriptManagerImpl;
import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ScriptContextBuilder {
    private final ScriptManager manager = new ScriptManagerImpl();
    private ScriptType type;
    private String key;
    private String path;
    private String absolutePath;
    private File context;
    private String name;
    private List<String> params = new ArrayList<>();
    
    private ScriptContextBuilder() {
    }
    
    public static ScriptContextBuilder builder() {
        return new ScriptContextBuilder();
    }
    
    public ScriptContextBuilder key(String key) {
        this.key = key;
        Script script = manager.getScriptByKey(key);
        if (script != null) {
            return script(script);
        }
        return this;
    }
    
    public ScriptContextBuilder path(String path) {
        File file = new File(path);
        if (file.exists()) {
            return shell(file);
        }
        return this;
    }
    
    public ScriptContextBuilder param(String param, String arg) {
        if (StrUtil.isBlank(param) || StrUtil.isBlank(arg)) {
            return this;
        }
        this.params.add(param);
        this.params.add(arg);
        return this;
    }
    
    private ScriptContextBuilder script(Script script) {
        this.path = script.getPath();
        this.name = script.getName();
        this.key = script.getKey();
        this.type = script.getType();
        switch (type) {
            case FILE:
                this.context = new File(script.getPath());
                this.absolutePath = this.context.getAbsolutePath();
                break;
            case TEMP:
                temp(script.getKey(), script.getContent());
        }
        return this;
    }
    
    private ScriptContextBuilder shell(File file) {
        if (file == null) {
            throw new IllegalArgumentException("shell file is null");
        }
        this.absolutePath = file.getAbsolutePath();
        this.path = file.getPath();
        this.name = file.getName();
        this.type = ScriptType.FILE;
        this.context = file;
        try {
            Stream<String> lines = Files.lines(file.toPath());
            lines.forEach(line -> {
                if (line.startsWith("#!key ")) {
                    this.key = line.replace("#!key ", "");
                }
            });
        } catch (Exception e) {
            log.error("read shell is null");
        }
        if (StrUtil.isBlank(key)) {
            throw new IllegalArgumentException("current shell not has '#!key '");
        }
        return this;
    }
    
    public ScriptContextBuilder temp(String key, String context) {
        this.type = ScriptType.TEMP;
        this.name = "temp-" + UUID.randomUUID() + "-" + key;
        this.key = this.name;
        File file = FileUtil.touch("./temp/" + name + ".sh");
        this.context = FileUtil.writeBytes(context.getBytes(), file);
        this.absolutePath = file.getAbsolutePath();
        this.path = file.getPath();
        return this;
    }
    
    public ScriptContext build() {
        ScriptContext script = new ScriptContext();
        script.setType(type);
        script.setKey(key);
        script.setPath(path);
        script.setAbsolutePath(absolutePath);
        script.setContext(context);
        script.setName(name);
        script.setArgs(params.toArray(new String[0]));
        script.setScript(script.getScript());
        
        Script s = manager.getScriptByKey(key);
        if (s == null) {
            manager.create(script.getScript());
        }
        return script;
    }
    
    public void execute(){
        build().execute();
    }
    
    
}
