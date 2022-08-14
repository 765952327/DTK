package cn.welsione.dtk.script;

import org.springframework.stereotype.Component;

@Component
public class ScriptRecognizer {
    public int getType(String path) {
        if (path.endsWith(".sh")) {
            return 0;
        }
        if (path.endsWith(".jar")) {
            return 1;
        }
        throw new IllegalArgumentException("invalid script");
    }
}
