package com.heavenscode.sungoldsolar.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of InvertersSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class InvertersSearchRepositoryMockConfiguration {

    @MockBean
    private InvertersSearchRepository mockInvertersSearchRepository;

}
