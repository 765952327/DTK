package cn.welsione.dtk.script.selector;

import cn.welsione.dtk.script.ScriptManagerService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ScriptScanner {
    @Autowired
    private ScriptManagerService scriptManagerService;
    
    @PostConstruct
    public void init(){
        File file = new File("shell/macos/");
        try {
            Stream<Path> list = Files.list(file.toPath());
            Set<String> keys = new HashSet<>();
            if (list != null){
                list.forEach(path -> {
                    String key = getKey(path);
                    if (keys.contains(key)){
                        return;
                    } else {
                        keys.add(key);
                    }
                    String params = getParams(path);
                    scriptManagerService.add(key, params, path.toAbsolutePath().toString());
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private String getKey(Path path) {
        String name = path.getFileName().toString();
        String[] split = name.split("\\.");
        return "def-mac-" + split[0];
    }
    private String getParams(Path path){
        try {
            Stream<String> lines = Files.lines(path);
            AtomicReference<String> params = new AtomicReference<>(null);
            if ( lines != null){
                lines.forEach(line -> {
                    if (line.startsWith("#!param")){
                        params.set(line.replace("#!param ", "").replaceAll(" ", ","));
                        return;
                    }
                });
            }
            return params.get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
