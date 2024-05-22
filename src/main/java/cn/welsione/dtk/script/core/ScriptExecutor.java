package cn.welsione.dtk.script.core;

/**
 *  脚本执行器
 */
public interface ScriptExecutor {
    /**
     * 执行脚本
     * @param script 脚本上下文
     */
    void execute(ScriptContext script);
}
