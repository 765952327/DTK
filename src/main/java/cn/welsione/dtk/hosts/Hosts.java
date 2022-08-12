package cn.welsione.dtk.hosts;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "HOSTS")
@Data
public class Hosts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private Integer port;
    private String username;
    private String password;
}
