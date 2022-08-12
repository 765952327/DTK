package cn.welsione.dtk.hosts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HostsRepository extends JpaRepository<Hosts,Long> {
    Hosts save(Hosts entity);
}
