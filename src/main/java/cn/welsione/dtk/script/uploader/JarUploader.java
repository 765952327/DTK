package cn.welsione.dtk.script.uploader;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.Script;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Component
public class JarUploader implements ScriptUploader {
    private static final String PATH = "script/jar/";
    private static final String FILE_TYPE = ".jar";
    
    @Override
    public String upload(File script) {
        String path = PATH + script.getName();
        File newFile = FileUtil.newFile(path);
        FileUtil.copy(script, newFile, false);
        return PREFIX + newFile.getAbsolutePath();
    }
    
    @Override
    public boolean check(File script) {
        return script.getName().endsWith(FILE_TYPE);
    }
}
