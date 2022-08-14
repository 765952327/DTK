package cn.welsione.dtk.script;

import cn.hutool.core.io.FileUtil;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JarScriptExecutor implements ScriptExecutor {
    @Override
    public void execute(Script entity, String ...command) {
        byte[] script = entity.getScript();
        File file = FileUtil.createTempFile("java", ".jar", new File("./java/"), false);
        FileUtil.writeBytes(script, file);
        List<String> cmd = new ArrayList<>();
        cmd.add("java");
        cmd.add("-jar");
        cmd.add(file.getAbsolutePath());
        cmd.addAll(Arrays.asList(command));
        String[] cmds = cmd.toArray(new String[0]);
        ShellUtil.execInherit(cmds);
        file.deleteOnExit();
    }
    
    @Override
    public boolean check(Script script) {
        return script.getType() == 1;
    }
}
