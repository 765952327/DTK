package cn.welsione.dtk.serivce;

public interface FrontService {
    
    void pull(String proj);
    
    void pull(String proj,String env);
    
    void ln(String origin,String target);
    
    void link(String proj,String config);
}
