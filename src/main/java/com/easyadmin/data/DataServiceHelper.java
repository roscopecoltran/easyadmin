package com.easyadmin.data;

import com.easyadmin.schema.SchemaService;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.domain.Filter;
import com.easyadmin.schema.enums.OperatorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by gongxinyi on 2017-11-10.
 */
@Component
public class DataServiceHelper {
    @Autowired
    SchemaService schemaService;

    /**
     * build filter object , contains key、operator 、value
     *
     * @param entry
     * @param fieldMap
     * @return
     */
    public Filter getFilter(Map.Entry<String, Object> entry, Map<String, Field> fieldMap) {
        if (entry.getKey().endsWith("_gte") || entry.getKey().endsWith("_lte")) {
            int idx = entry.getKey().lastIndexOf("_");
            String key = entry.getKey().substring(0, idx);
            String operator = entry.getKey().substring(idx + 1);
            if (fieldMap.containsKey(key)) {
                return new Filter(key, OperatorEnum.valueOf(operator), entry.getValue());
            }
        }
        return new Filter(entry.getKey(), OperatorEnum.eq, entry.getValue());
    }

    public Map<String, Field> getFieldIdMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().collect(Collectors.toMap(Field::getName, Function.identity(),
                (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                },
                TreeMap::new));
    }

    public Map<String, Field> getPrimaryFieldIdMap(String entity) {
        List<Field> fields = schemaService.findFields(entity);
        return fields.stream().filter(field -> field.getIsPartOfPrimaryKey()).collect(Collectors.toMap(Field::getName, Function.identity(),
                (v1, v2) -> {
                    throw new RuntimeException(String.format("Duplicate key for values %s and %s", v1, v2));
                },
                TreeMap::new));
    }
}
