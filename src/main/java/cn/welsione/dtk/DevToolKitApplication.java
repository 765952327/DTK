package cn.welsione.dtk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@SpringBootApplication
public class DevToolKitApplication {
    public static void main(String[] args) {
        // 指定禁用内置命令列表
        String[] disableCmds = new String[]{
                "--spring.shell.command.stacktrace.enabled=false"
        };
        // 重新拼接启动参数
        String[] fullArgs = StringUtils.concatenateStringArrays(args, disableCmds);
        SpringApplication.run(DevToolKitApplication.class, fullArgs);
    }
}
