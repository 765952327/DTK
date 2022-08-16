package cn.welsione.dtk.script.uploader;

import java.io.File;

public interface ScriptUploader {
    static final String PREFIX = "{PATH}";
    String upload(File script);
    
    boolean check(File script);
}
