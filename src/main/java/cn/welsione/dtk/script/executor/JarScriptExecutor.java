package cn.welsione.dtk.script.executor;

import cn.hutool.core.io.FileUtil;
import cn.welsione.dtk.script.Script;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JarScriptExecutor implements ScriptExecutor {
    @Override
    public void execute(Script entity, String ...command) {
        File file = FileUtil.file(entity.getPath());
        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-jar");
        cmd.add(file.getAbsolutePath());
        String[] split = entity.getParams().split(" ");
        cmd.addAll(Arrays.asList(split));
        cmd.addAll(Arrays.asList(command));
        String[] cmds = cmd.toArray(new String[0]);
        ShellUtil.execInherit(cmds);
    }
    
    @Override
    public boolean check(Script script) {
        return script.getType() == 1;
    }
}
