package cn.welsione.dtk.serivce.impl;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.config.ConfigService;
import cn.welsione.dtk.config.ConfigServiceImpl;
import cn.welsione.dtk.script.core.ScriptContext;
import cn.welsione.dtk.script.core.ScriptContextBuilder;
import cn.welsione.dtk.serivce.FrontService;
import java.io.File;

public class FrontServiceImpl implements FrontService {
    private static final String FRONT_BASE_PATH = "front.base.path";
    private static final String RESOURCE_BASE_PATH = "resource.base.path";
    private static final String RESOURCE_PATH = "resource.path";
    
    
    private static final ConfigService configService = new ConfigServiceImpl();
    
    @Override
    public void pull(String proj) {
        pull(proj, null);
    }
    
    @Override
    public void pull(String proj, String env) {
        ScriptContextBuilder.builder()
                .key("front_pull")
                .param("-d", configService.getConfig(FRONT_BASE_PATH, String.class))
                .param("-p", proj)
                .param("-e", null)
                .execute();
    }
    
    @Override
    public void ln(String origin, String target) {
        FileUtil.del(target);
        String cmd = "ln -s %s %s";
        ScriptContext script = ScriptContextBuilder.builder().temp("ln-mac", String.format(cmd, origin, target)).build();
        script.execute();
    }
    
    @Override
    public void link(String proj, String config) {
        String targetBase = configService.getConfig(config, String.class) + configService.getConfig(RESOURCE_PATH, String.class);
        
        File[] links = FileUtil.ls(targetBase);
        
        for (File link : links) {
            System.out.printf("del link %s\n", link.getAbsolutePath());
            link.deleteOnExit();
        }
        
        
        //  无需更新的
        String base = configService.getConfig(RESOURCE_BASE_PATH, String.class);
        File[] baseResources = FileUtil.ls(base);
        for (File resource : baseResources) {
            String path = resource.getAbsolutePath();
            String target = targetBase + path.replace(base, "");
            ln(path, target);
        }

        // 需要更新的
        String source = configService.getConfig(FRONT_BASE_PATH, String.class) + "/" + proj;
        File[] resources = FileUtil.ls(source);
        for (File resource : resources) {
            String path = resource.getAbsolutePath();
            String target = targetBase + path.replace(source, "");
            ln(path, target);
        }
    }
    
    public static void main(String[] args) {
        FrontService service = new FrontServiceImpl();
        service.pull("RELEASE438");
//        service.link("RELEASE438","private1");
//        service.link("RELEASE438","private2");
//        service.link("RELEASE44X", "private3");
//        service.link("RELEASE438","private4");
//        service.link("RELEASE438","private5");
//        String[] projs = {"RELEASE44X"};
//        for (String proj : projs) {
//            Thread thread = new Thread(()->{
//                service.pull(proj);
//            });
//            thread.start();
//        }
//        service.link("RELEASE438", "private3");

//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private1") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private1") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private2") + "/data");
////        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private2") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private3") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private3") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private4") + "/data");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data_bak", configService.getConfig("private4") + "/data_bak");
//        service.ln("/Users/weigaolei/CodeSpace/WorkSpace/qys-private/qys-data/data", configService.getConfig("private5") + "/data");
    }
    
}