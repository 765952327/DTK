package cn.welsione.dtk.serivce;

import cn.welsione.dtk.config.ConfigService;
import cn.welsione.dtk.config.ConfigServiceImpl;
import java.util.ArrayList;
import java.util.List;

public class DataBaseService {
    private static final String CONFIG_PATHS = "proj.config.properties.path";
    private static final ConfigService configService = new ConfigServiceImpl();
    private static final List<String> paths = new ArrayList<>();
    
    static {
        ArrayList list = configService.getConfig(CONFIG_PATHS, ArrayList.class);
        paths.addAll(list);
        if (paths.isEmpty()){
            paths.add("config/application.properties");
            paths.add("privapp/src/main/resources/application-develop.properties");
            paths.add("privapp/src/main/resources/application.properties");
            paths.add("privoss/src/main/resources/application-develop.properties");
            paths.add("privoss/src/main/resources/application.properties");
            paths.add("privopen/src/main/resources/application-develop.properties");
            paths.add("privopen/src/main/resources/application.properties");
            paths.add("sign/src/main/resources/application-develop.properties");
            paths.add("oss/src/main/resources/application-develop.properties");
            configService.setDefault(CONFIG_PATHS,paths,"默认项目配置文件路径配置");
        }
    }
    
    
    public static void main(String[] args) {
        DataBaseService dataBaseService = new DataBaseService();
        
    }
}
