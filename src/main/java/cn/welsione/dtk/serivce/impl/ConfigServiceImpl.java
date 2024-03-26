package cn.welsione.dtk.serivce.impl;

import cn.hutool.core.io.FileUtil;
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
                .temp("open_config", "open " + config, new String[]{"|","grep","test"}).build();
        executor.execute(script);
    }
    
    @Override
    public String getConfig(String key) {
        String fileContent = FileUtil.readUtf8String(config);
        Map map = JSONUtil.toBean(fileContent, Map.class);
        return (String) map.get(key);
    }
    
    public static void main(String[] args) {
        ConfigService configService = new ConfigServiceImpl();
        String test = configService.getConfig("test");
        System.out.println(test);
    }
}
