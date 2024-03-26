package cn.welsione.dtk.script.executor;

import cn.welsione.dtk.script.Script;
import cn.welsione.dtk.script.ScriptExecutor;
import cn.welsione.dtk.script.ScriptType;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShellScriptExecutor implements ScriptExecutor {
    @Override
    public void execute(Script script) {
        File file = script.getContext();
        List<String> cmd = new ArrayList<>();
        cmd.add("sh");
        cmd.add(file.getAbsolutePath());
        cmd.addAll(Arrays.asList(interleaveArrays(script.getParams(), script.getArgs())));
        ShellUtil.execInherit(cmd.toArray(new String[0]));
        script.close();
    }
    
    private String[] interleaveArrays(String[] arr1, String[] arr2) {
        if (arr1 == null || arr1.length == 0) {
            return arr2;
        }
        
        if (arr2 == null || arr2.length == 0) {
            return arr1;
        }
        
        int totalLength = arr1.length + arr2.length;
        String[] result = new String[totalLength];
        
        int index = 0;
        int i = 0;
        int j = 0;
        
        while (i < arr1.length && j < arr2.length) {
            result[index++] = arr1[i++];
            result[index++] = arr2[j++];
        }
        
        while (i < arr1.length) {
            result[index++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            result[index++] = arr2[j++];
        }
        
        return result;
    }
    

    
    
}
