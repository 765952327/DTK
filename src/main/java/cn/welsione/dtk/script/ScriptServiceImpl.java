package cn.welsione.dtk.script;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScriptServiceImpl implements ScriptService{
    @Autowired
    private ScriptRepository scriptRepository;
    @Override
    public Script create(String key, String params, String script, int type) {
        if (findByKey(key) != null){
            throw new IllegalArgumentException("--key " + key + " 已存在");
        }
        Script entity = new Script();
        entity.setCreatetime(new Date());
        entity.setSkey(key);
        entity.setParams(params);
        entity.setScript(script);
        entity.setType(type);
        return scriptRepository.save(entity);
    }
    
    @Override
    public Script findByKey(String key) {
        return scriptRepository.findBySkey(key);
    }
}
