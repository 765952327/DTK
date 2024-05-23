package cn.welsione.dtk.framework;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalPersister<T extends Entity> implements Persister<T> {
    private static final String base_dir = System.getProperty("user.dir") + File.separator + "data";
    private static final Logger log = LoggerFactory.getLogger(LocalPersister.class);
    private final Map<String, T> data = new HashMap<>();
    private final File file;
    
    public LocalPersister(Class<?> type) {
        file = new File(base_dir + File.separator + type.getSimpleName() + ".json");
        try {
            if (FileUtil.exist(file)) {
                String json = FileUtil.readUtf8String(file);
                JSONObject entries = JSONUtil.parseObj(json);
                if (entries.isEmpty()) {
                    return;
                }
                entries.forEach((k, v) -> {
                    JSONObject entry = (JSONObject) v;
                    data.put(k, (T) JSONUtil.toBean(entry, type));
                });
            } else {
                FileUtil.writeUtf8String(JSONUtil.toJsonStr(data), file);
            }
        } catch (Exception e) {
            log.error("init local persister error", e);
        }
    }
    
    private synchronized void flushData() {
        FileUtil.writeUtf8String(JSONUtil.toJsonStr(data), file);
    }
    
    
    @Override
    public void create(T t) {
        if (StrUtil.isBlank(t.getUuid())) {
            t.setUuid(StrUtil.uuid());
        }
        data.put(t.getUuid(), t);
        flushData();
    }
    
    @Override
    public void update(T t) {
        if (t.getUuid() == null) {
            throw new IllegalArgumentException("uuid is null");
        }
        data.put(t.getUuid(), t);
        flushData();
    }
    
    @Override
    public List<T> queryAll() {
        return new ArrayList<>(data.values());
    }
    
    @Override
    public List<T> query(T condition) {
        List<T> result = new ArrayList<>();
        Collection<T> values = data.values();
        if (values.isEmpty()) {
            return new ArrayList<>();
        }
        Field[] fields = condition.getClass().getDeclaredFields();
        values.forEach(item -> {
            boolean allMatch = Arrays.stream(fields).allMatch(field -> {
                try {
                    field.setAccessible(true);
                    Object value = field.get(condition);
                    if (value == null) {
                        return true;
                    }
                    return sameVal(condition, item, field);
                } catch (Exception e) {
                    log.error("query error", e);
                }
                return false;
            });
            if (allMatch) {
                result.add(item);
            }
        });
        
        return result;
    }
    
    @Override
    public void delete(T t) {
        data.remove(t.getUuid());
        flushData();
    }
    
    @Override
    public T get(String uuid) {
        return data.get(uuid);
    }
    
    @Override
    public T selectOne(T condition) {
        List<T> query = query(condition);
        if (query.isEmpty()){
            return null;
        }
        if (query.size() == 1) {
            return query.get(0);
        }
        throw new RuntimeException("select one but find two");
    }
    
    private boolean sameVal(T condition, T item, Field field) throws IllegalAccessException {
        Object val1 = field.get(condition);
        Object val2 = field.get(item);
        return val1.equals(val2);
    }
}
