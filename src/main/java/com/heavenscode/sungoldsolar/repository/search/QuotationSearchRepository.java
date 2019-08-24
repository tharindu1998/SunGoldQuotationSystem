package com.heavenscode.sungoldsolar.repository.search;

import com.heavenscode.sungoldsolar.domain.Quotation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Quotation entity.
 */
public interface QuotationSearchRepository extends ElasticsearchRepository<Quotation, String> {
}
