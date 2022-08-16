package cn.welsione.dtk.script.uploader;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ShellUploader implements ScriptUploader {
    private static final String PATH = "script/shell/";
    private static final String FILE_TYPE = ".sh";
    
    @Override
    public String  upload(File script) {
        String path = PATH + script.getName();
        File file = FileUtil.newFile(path);
        FileUtil.copy(script, file, false);
        return PREFIX + file.getAbsolutePath();
    }
    
    @Override
    public boolean check(File script) {
        return script.getName().endsWith(FILE_TYPE);
    }
}
