package cn.welsione.dtk.script;

/**
 *  脚本执行器
 */
public interface ScriptExecutor {
    void execute(Script script, String ...command);
    
    boolean check(Script script);
}
