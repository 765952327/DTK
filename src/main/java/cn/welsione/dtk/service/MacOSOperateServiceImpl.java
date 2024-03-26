package cn.welsione.dtk.service;

import cn.welsione.dtk.script.ScriptManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MacOSOperateServiceImpl implements OperateService{
    private static final String prefix = "def-mac-";
    @Autowired
    private ScriptManagerService scriptManagerService;
    
    @Override
    public boolean ln(String origin, String target) {
        return scriptManagerService.execute(prefix + "ln", origin, target);
    }
}
