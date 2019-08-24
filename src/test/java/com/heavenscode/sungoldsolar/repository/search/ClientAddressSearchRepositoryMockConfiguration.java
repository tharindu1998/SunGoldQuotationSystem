package com.heavenscode.sungoldsolar.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of ClientAddressSearchRepository to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ClientAddressSearchRepositoryMockConfiguration {

    @MockBean
    private ClientAddressSearchRepository mockClientAddressSearchRepository;

}
