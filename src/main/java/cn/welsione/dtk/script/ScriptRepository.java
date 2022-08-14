package cn.welsione.dtk.script;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScriptRepository extends JpaRepository<Script,Long> {
    Script findBySkey(String key);
}
