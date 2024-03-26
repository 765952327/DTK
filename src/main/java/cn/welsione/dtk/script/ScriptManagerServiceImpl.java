package cn.welsione.dtk.script;

import cn.welsione.dtk.script.executor.ScriptExecutor;
import cn.welsione.dtk.script.uploader.ScriptUploader;
import java.io.File;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScriptManagerServiceImpl implements ScriptManagerService{
    @Autowired
    private ScriptService scriptService;
    @Autowired
    private ScriptRecognizer scriptRecognizer;
    @Autowired
    private List<ScriptExecutor> scriptExecutors;
    @Autowired
    private List<ScriptUploader> scriptUploaders;
    @Override
    public synchronized Script add(String key, String params, String path) {
        Script script = scriptService.findByKey(key);
        if (script != null){
            return script;
        }
        int type = scriptRecognizer.getType(path);
        File file = new File(path);
        if (!file.exists()) {
            throw new IllegalArgumentException("找不到脚本文件");
        }
        String upload = null;
        for (ScriptUploader uploader : scriptUploaders){
            if (uploader.check(file)){
                upload = uploader.upload(file);
                break;
            }
        }
        return scriptService.create(key, params, upload, type);
    }
    
    @Override
    public boolean execute(String key, String... command) {
        Script entity = scriptService.findByKey(key);
        if (entity == null){
            throw new IllegalArgumentException("脚本不存在");
        }
        scriptExecutors.forEach(executor -> {
            if (executor.check(entity)){
                executor.execute(entity,command);
            }
        });
        return true;
    }
}
