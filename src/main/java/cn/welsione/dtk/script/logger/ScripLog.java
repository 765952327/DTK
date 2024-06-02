package cn.welsione.dtk.script.logger;

import cn.welsione.dtk.framework.Entity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ScripLog extends Entity {
    private String scriptId;
    private String executor;
    private Date createTime;
}
