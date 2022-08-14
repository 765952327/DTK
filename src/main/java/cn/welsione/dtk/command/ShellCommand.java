package cn.welsione.dtk.command;

import cn.welsione.dtk.script.ScriptManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent("脚本扩展")
@ShellCommandGroup("扩展")
public class ShellCommand {
    @Autowired
    private ScriptManagerService scriptManagerService;
    @ShellMethod(value = "创建新的扩展脚本")
    public void create(@ShellOption("--key") String key,
                       @ShellOption(value = "--command",defaultValue = "",help = "默认参数") String params,
                       @ShellOption(value = "--path",help = "脚本本地路径") String path){
        scriptManagerService.add(key, params, path);
    }
    
    @ShellMethod(value = "执行扩展脚本")
    public void exec(@ShellOption("--key") String key,
                       @ShellOption(value = "--command",defaultValue = "") String[] command){
        scriptManagerService.execute(key, command);
    }
}
