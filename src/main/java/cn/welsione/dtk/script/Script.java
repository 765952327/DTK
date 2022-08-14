package cn.welsione.dtk.script;

import lombok.Data;

import javax.persistence.*;
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
    private byte[] script;
    private Date createtime;
    private int type; // shell:0,jar:1
}
