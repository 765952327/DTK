package cn.welsione.dtk.framework;

import java.io.Serializable;

public abstract class Entity implements Serializable {
    protected String uuid;
    
    public String getUuid() {
        return uuid;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
