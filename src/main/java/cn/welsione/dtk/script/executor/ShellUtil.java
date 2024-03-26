package cn.welsione.dtk.script.executor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;


@Slf4j
public class ShellUtil {
    public static void execInherit(String... command) {
        log.warn("{} [important] Java shell has some unknown problem, please use with caution!!!", Marker.ANY_MARKER);
        log.info("{} {}", Marker.ANY_MARKER, getCmd(command));
        Runnable exec = () -> {
            ProcessBuilder builder = new ProcessBuilder();
            try {
                builder.command(command)
                        .redirectErrorStream(true)
                        .inheritIO();
                Process process = builder.start();
                process.waitFor();
                log.info("Process finished with exit code {}", process.exitValue());
            } catch (Exception e) {
                log.error("Process finished with error {}", e.getMessage());
            }
        };
        exec.run();
    }
    
    private static String getCmd(String... command) {
        StringBuilder res = new StringBuilder();
        for (String cmd : command) {
            res.append(cmd).append(" ");
        }
        return res.deleteCharAt(res.length() - 1).toString();
    }
}
