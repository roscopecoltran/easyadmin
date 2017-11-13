package com.easyadmin.schema;

import com.easyadmin.cloud.DataSource;
import com.easyadmin.cloud.Tenant;
import com.easyadmin.consts.Constants;
import com.easyadmin.schema.domain.Entity;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.enums.Component;
import com.easyadmin.schema.enums.EsColumnType;
import com.easyadmin.service.EsService;
import com.easyadmin.service.SequenceService;
import com.easyadmin.service.SysService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by gongxinyi on 2017-11-13.
 */
@Slf4j
@org.springframework.stereotype.Component
public class EsSchemaSyncServiceImpl implements ISchemaSyncService {

    @Autowired
    EsService esService;
    @Autowired
    SequenceService sequenceService;
    @Autowired
    SchemaService schemaService;
    @Autowired
    Map<EsColumnType, Component> esFieldTypeMap;
    @Autowired
    SysService sysService;

    @Override
    public void syncSchemas() {
        List<Entity> entities = schemaService.findEntities();
        DataSource dataSource = Tenant.get().getCurrentDataSource();
        IndexMetaData index = esService.getEsClient().admin().cluster().prepareState().execute()
                .actionGet().getState().getMetaData().getIndices().get(dataSource.getIndexName());
        if (index == null) {
            throw new RuntimeException("not find index:" + dataSource.getIndexName());
        }
        ImmutableOpenMap<String, MappingMetaData> mappings = index.getMappings();
        mappings.valuesIt().forEachRemaining(mappingMetaData -> {
            try {
                addEntityAndFields(mappingMetaData, dataSource.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void addEntityAndFields(MappingMetaData mappingMetaData, String dataSourceId) throws IOException {
        Entity entity = new Entity();
        entity.setId(Constants.ENTITY_NAME_PREFIX + sequenceService.getNextSequence(Constants.SYS_COL_Entity + "_id").toString());
        entity.setName(mappingMetaData.type());
        entity.setLabel(entity.getName());
        entity.setDataSourceId(dataSourceId);
        Map<String, Object> mappingMap = mappingMetaData.sourceAsMap();
        Map<String, Object> fieldTypeMap = (Map<String, Object>) mappingMap.get("properties");
        List<Field> fields = new ArrayList<>();
        fieldTypeMap.forEach((k, v) -> {
            log.info("field name:{},type:{}", k, v);
            Map<String, String> fieldType = ((Map<String, String>) v);
            Field field = new Field();
            field.setComponent(esFieldTypeMap.get(EsColumnType.valueOf("_" + fieldType.get("type"))));
            field.setName(k);
            field.setLabel(k);
            field.setDataSourceId(dataSourceId);
            field.setEid(entity.getId());
            field.setId(Constants.FIELD_NAME_PREFIX + sequenceService.getNextSequence(Constants.SYS_COL_Field + Constants._id).toString());
            fields.add(field);
        });
        sysService.getTenantDataStore().save(entity);
        sysService.getTenantDataStore().save(fields);
    }
}
