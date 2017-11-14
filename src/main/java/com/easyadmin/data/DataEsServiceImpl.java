package com.easyadmin.data;

import com.easyadmin.cloud.Tenant;
import com.easyadmin.schema.domain.Field;
import com.easyadmin.schema.domain.Filter;
import com.easyadmin.service.EsService;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author gongxinyi
 * @date 2017-11-13
 */
@Component
public class DataEsServiceImpl implements IDataService {
    @Autowired
    EsService esService;
    @Autowired
    DataServiceHelper dataServiceHelper;

    @Override
    public List<Map<String, Object>> list(String entity, Map<String, Object> filters, RequestScope requestScope) {
        SearchResponse searchResponse = getSearchResponse(entity, filters, requestScope);

        List<Map<String, Object>> data = new ArrayList<>();
        searchResponse.getHits().forEach(searchHit -> {
            Map<String, Object> record = searchHit.getSource();
            record.put("id", searchHit.getId());
            data.add(record);
        });
        return data;
    }

    private SearchResponse getSearchResponse(String entity, Map<String, Object> filters, RequestScope requestScope) {
        Map<String, Field> fieldMap = dataServiceHelper.getFieldIdMap(entity);
        SearchRequestBuilder searchRequestBuilder = esService.getEsClient()
                .prepareSearch(Tenant.get().getCurrentDataSource().getIndexName())
                .setTypes(entity)
                .setQuery(buildQuery(filters, fieldMap)
                )
                .setSearchType(SearchType.QUERY_THEN_FETCH);
        if (requestScope != null) {
            searchRequestBuilder.setFrom(requestScope.get_start())
                    .setSize(requestScope.getLimit())
                    .addSort(requestScope.get_sort(), "DESC".equalsIgnoreCase(requestScope.get_order()) ? SortOrder.DESC : SortOrder.ASC);
        }

        return searchRequestBuilder.execute().actionGet();
    }

    private QueryBuilder buildQuery(Map<String, Object> filters, Map<String, Field> fieldMap) {
        BoolQueryBuilder boolqueryBuilder = QueryBuilders.boolQuery();
        filters.entrySet().forEach(entry -> {
            Filter filter = dataServiceHelper.getFilter(entry, fieldMap);
            QueryBuilder queryBuilder = buildEsQuery(filter);
            boolqueryBuilder.must(queryBuilder);
        });
        return boolqueryBuilder;
    }

    private QueryBuilder buildEsQuery(Filter filter) {
        QueryBuilder queryBuilder;
        switch (filter.getOperatorEnum()) {
            case eq:
                queryBuilder = QueryBuilders.termQuery(filter.getKey(), filter.getValue());
                break;
            case gte:
                queryBuilder = QueryBuilders.rangeQuery(filter.getKey()).from(filter.getValue());
                break;
            case lte:
                queryBuilder = QueryBuilders.rangeQuery(filter.getKey()).to(filter.getValue());
                break;
            default:
                queryBuilder = QueryBuilders.termQuery(filter.getKey(), filter.getValue());
                break;
        }
        return queryBuilder;
    }

    @Override
    public long count(String entity, Map<String, Object> filters) {
        SearchResponse searchResponse = getSearchResponse(entity, filters, null);
        return searchResponse.getHits().getTotalHits();
    }

    @Override
    public Map<String, Object> findOne(String entity, String id) {
        GetResponse getResponse = esService.getEsClient().prepareGet(Tenant.get().getCurrentDataSource().getIndexName(), entity, id).get();
        return getResponse.getSourceAsMap();
    }

    @Override
    public Map<String, Object> save(String entity, Map<String, Object> data) {
        esService.getEsClient().prepareIndex(Tenant.get().getCurrentDataSource().getIndexName(), entity).setSource(data).get();
        return data;
    }

    @Override
    public Map<String, Object> update(String entity, String id, Map<String, Object> data) {
        esService.getEsClient()
                .prepareUpdate(Tenant.get().getCurrentDataSource().getIndexName(), entity, id)
                .setDoc(data)
                .get();
        return data;
    }

    @Override
    public String delete(String entity, String id) {
        DeleteResponse response = esService.getEsClient()
                .prepareDelete(Tenant.get().getCurrentDataSource().getIndexName(), entity, id)
                .get();
        return id;
    }
}
