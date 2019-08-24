package com.heavenscode.sungoldsolar.repository.search;

import com.heavenscode.sungoldsolar.domain.Inverters;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Inverters entity.
 */
public interface InvertersSearchRepository extends ElasticsearchRepository<Inverters, String> {
}
