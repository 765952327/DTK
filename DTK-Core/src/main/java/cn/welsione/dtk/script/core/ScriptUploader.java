package cn.welsione.dtk.script.core;

import java.io.File;

public interface ScriptUploader {
    static final String PREFIX = "{PATH}";
    String upload(File script);
    
    boolean check(File script);
}
