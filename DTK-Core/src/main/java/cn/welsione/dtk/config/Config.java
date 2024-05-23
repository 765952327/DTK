package cn.welsione.dtk.config;

import cn.welsione.dtk.framework.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config extends Entity {
    private String name;
    private String value;
    private String comment;
}
