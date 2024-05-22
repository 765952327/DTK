package cn.welsione.dtk.script.core;

import cn.welsione.dtk.framework.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Script extends Entity {
    private String key;
    private String name;
    private String path;
    private String content;
    private ScriptType type;
}
