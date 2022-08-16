package cn.welsione.dtk.script;

import cn.hutool.core.util.StrUtil;
import cn.welsione.dtk.script.uploader.ScriptUploader;
import lombok.Data;

import javax.persistence.*;
import java.io.File;
import java.util.Arrays;
import java.util.Date;

@Entity
@Table(name = "SCRIPT")
@Data
public class Script {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String skey;
    private String params;
    private String script;
    private Date createtime;
    private int type; // shell:0,jar:1
    
    public String getPath(){
        String path = null;
        if (StrUtil.isNotBlank(script)){
            path = script;
        }
        if (path != null && path.contains(ScriptUploader.PREFIX)){
            return path.substring(ScriptUploader.PREFIX.length());
        }
        return System.getProperty("user.dir") + File.separator +path;
    }
}
