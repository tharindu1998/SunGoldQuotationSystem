package com.heavenscode.sungoldsolar.repository.search;

import com.heavenscode.sungoldsolar.domain.Panels;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Panels entity.
 */
public interface PanelsSearchRepository extends ElasticsearchRepository<Panels, String> {
}
