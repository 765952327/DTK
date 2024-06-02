package cn.welsione.dtk.config;

import cn.hutool.json.JSONUtil;
import cn.welsione.dtk.framework.LocalPersister;
import cn.welsione.dtk.framework.Persister;
import cn.welsione.dtk.script.core.ScriptContext;
import cn.welsione.dtk.script.core.ScriptContextBuilder;
import cn.welsione.dtk.script.core.ScriptExecutor;
import cn.welsione.dtk.script.core.executor.ShellScriptExecutor;

public class ConfigServiceImpl implements ConfigService {
    private static final String DIR = "config.dir";
    private static final Persister<Config> persister = new LocalPersister<>(Config.class);
    
    private static final ScriptExecutor executor = new ShellScriptExecutor();
    
    private Config findByKey(String key) {
        Config condition = new Config();
        condition.setName(key);
        return persister.selectOne(condition);
    }
    
    @Override
    public void open() {
        Config config = findByKey(DIR);
        ScriptContext script = ScriptContextBuilder.builder()
                .temp("open_config", "open " + config.getValue()).build();
        executor.execute(script);
    }
    
    @Override
    public <T> T getConfig(String key, Class<T> clazz) {
        Config config = findByKey(key);
        if (config == null) {
            throw new RuntimeException("config not found key: " + key);
        }
        if (clazz == String.class){
            return clazz.cast(config.getValue());
        }
        return JSONUtil.toBean(config.getValue(), clazz);
    }
    
    @Override
    public boolean setConfig(String key, String val) {
        Config config = findByKey(key);
        if (config == null) {
            config = new Config(key, val, null);
            persister.create(config);
            return true;
        }
        config.setValue(val);
        persister.update(config);
        return true;
    }
    
    @Override
    public <T> boolean setConfig(String key, T val) {
        Config config = findByKey(key);
        String json = JSONUtil.toJsonStr(val);
        if (config == null) {
            config = new Config(key, json, null);
            persister.create(config);
            return true;
        }
        config.setValue(json);
        persister.update(config);
        return true;
    }
    
    @Override
    public <T> boolean setDefault(String key, T val,String comment) {
        Config config = findByKey(key);
        if (config != null) {
            return true;
        }
        String value;
        if (val instanceof String){
            value = val.toString();
        } else {
            value = JSONUtil.toJsonStr(val);
        }
        config = new Config(key, value, comment);
        persister.create(config);
        return true;
    }
    
    public static void main(String[] args) {
        ConfigServiceImpl configService = new ConfigServiceImpl();
        configService.setConfig("resource.base.path","/Users/weigaolei/CodeSpace/WorkSpace/qys-front/base");
    }
}
