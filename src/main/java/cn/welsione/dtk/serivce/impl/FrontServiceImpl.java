package cn.welsione.dtk.serivce.impl;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.Script;
import cn.welsione.dtk.script.ScriptBuilder;
import cn.welsione.dtk.script.ScriptExecutor;
import cn.welsione.dtk.script.executor.ShellScriptExecutor;
import cn.welsione.dtk.serivce.ConfigService;
import cn.welsione.dtk.serivce.FrontService;
import java.io.File;

public class FrontServiceImpl implements FrontService {
    private static final ScriptExecutor executor = new ShellScriptExecutor();
    private static final ConfigService configService = new ConfigServiceImpl();
    @Override
    public void pull(String proj) {
        pull(proj, null);
    }
    
    @Override
    public void pull(String proj, String env) {
        Script script = ScriptBuilder.builder()
                .shell(new File("shell/pull.sh"))
                .param("-d", configService.getConfig("front_base_path"))
                .param("-p", proj)
                .param("-e", null)
                .build();
        executor.execute(script);
    }
    
    @Override
    public void ln(String origin, String target) {
        String cmd = "ln -s %s %s";
        Script script = ScriptBuilder.builder().temp("ln-mac", String.format(cmd, origin, target)).build();
        executor.execute(script);
    }
    
    @Override
    public void link(String proj, String config) {
        String targetBase = configService.getConfig(config) + configService.getConfig("resource_path");
        String source = configService.getConfig("front_base_path") + "/" + proj;
        // 需要更新的
        File[] resources = FileUtil.ls(source);
        for (File resource : resources) {
            String path = resource.getAbsolutePath();
            String target = targetBase + path.replace(source, "");
            ln(path, target);
        }
        // 无需更新的
        String base = configService.getConfig("resource_base_path");
        File[] baseResources = FileUtil.ls(base);
        for (File resource : baseResources) {
            String path = resource.getAbsolutePath();
            String target = targetBase + path.replace(base, "");
            ln(path, target);
        }
    }
    
    public static void main(String[] args) {
        FrontService service = new FrontServiceImpl();
//        service.link("RELEASE438","private1");
//        service.link("RELEASE438","private2");
//        service.link("RELEASE438","private3");
//        service.link("RELEASE438","private4");
//        service.link("RELEASE438","private5");
//        service.pull("RELEASE439");
        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private1") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private1") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private2") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private2") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private3") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private3") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private4") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private4") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private5") + "/data");
    }
    
}
