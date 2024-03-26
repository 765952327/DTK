package cn.welsione.dtk.serivce.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.welsione.dtk.script.Script;
import cn.welsione.dtk.script.ScriptBuilder;
import cn.welsione.dtk.script.ScriptExecutor;
import cn.welsione.dtk.script.executor.ShellScriptExecutor;
import cn.welsione.dtk.serivce.ConfigService;
import java.util.Map;

public class ConfigServiceImpl implements ConfigService {
    private static final String config = "./config/config.json";
    private static final ScriptExecutor executor = new ShellScriptExecutor();
    
    @Override
    public void open() {
        Script script = ScriptBuilder.builder()
                .temp("open_config", "open " + config).build();
        executor.execute(script);
    }
    
    @Override
    public String getConfig(String key) {
        String fileContent = FileUtil.readUtf8String(config);
        Map<String, String> map = JSONUtil.toBean(fileContent, new TypeReference<Map<String, String>>() {
        }, false);
        String val = map.get(key);
        if (StrUtil.isBlank(val)){
            throw new IllegalArgumentException("can not find config, key:" + key);
        }
        return val;
    }
    
    @Override
    public boolean setConfig(String key, String val) {
        String fileContent = FileUtil.readUtf8String(config);
        Map<String, String> map = JSONUtil.toBean(fileContent, new TypeReference<Map<String, String>>() {
        }, false);
        map.put(key, val);
        String json = JSONUtil.toJsonStr(map);
        FileUtil.writeBytes(json.getBytes(), FileUtil.newFile(config));
        return true;
    }
}
