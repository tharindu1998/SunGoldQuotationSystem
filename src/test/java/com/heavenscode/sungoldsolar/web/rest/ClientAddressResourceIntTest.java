package com.heavenscode.sungoldsolar.web.rest;

import com.heavenscode.sungoldsolar.SunGoldSolarApp;

import com.heavenscode.sungoldsolar.domain.ClientAddress;
import com.heavenscode.sungoldsolar.repository.ClientAddressRepository;
import com.heavenscode.sungoldsolar.repository.search.ClientAddressSearchRepository;
import com.heavenscode.sungoldsolar.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;


import static com.heavenscode.sungoldsolar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClientAddressResource REST controller.
 *
 * @see ClientAddressResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunGoldSolarApp.class)
public class ClientAddressResourceIntTest {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_POSTAL_CODE = 1;
    private static final Integer UPDATED_POSTAL_CODE = 2;

    @Autowired
    private ClientAddressRepository clientAddressRepository;

    /**
     * This repository is mocked in the com.heavenscode.sungoldsolar.repository.search test package.
     *
     * @see com.heavenscode.sungoldsolar.repository.search.ClientAddressSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClientAddressSearchRepository mockClientAddressSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restClientAddressMockMvc;

    private ClientAddress clientAddress;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientAddressResource clientAddressResource = new ClientAddressResource(clientAddressRepository, mockClientAddressSearchRepository);
        this.restClientAddressMockMvc = MockMvcBuilders.standaloneSetup(clientAddressResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientAddress createEntity() {
        ClientAddress clientAddress = new ClientAddress()
            .clientId(DEFAULT_CLIENT_ID)
            .address(DEFAULT_ADDRESS)
            .city(DEFAULT_CITY)
            .postalCode(DEFAULT_POSTAL_CODE);
        return clientAddress;
    }

    @Before
    public void initTest() {
        clientAddressRepository.deleteAll();
        clientAddress = createEntity();
    }

    @Test
    public void createClientAddress() throws Exception {
        int databaseSizeBeforeCreate = clientAddressRepository.findAll().size();

        // Create the ClientAddress
        restClientAddressMockMvc.perform(post("/api/client-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isCreated());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeCreate + 1);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testClientAddress.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testClientAddress.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testClientAddress.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);

        // Validate the ClientAddress in Elasticsearch
        verify(mockClientAddressSearchRepository, times(1)).save(testClientAddress);
    }

    @Test
    public void createClientAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientAddressRepository.findAll().size();

        // Create the ClientAddress with an existing ID
        clientAddress.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientAddressMockMvc.perform(post("/api/client-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClientAddress in Elasticsearch
        verify(mockClientAddressSearchRepository, times(0)).save(clientAddress);
    }

    @Test
    public void getAllClientAddresses() throws Exception {
        // Initialize the database
        clientAddressRepository.save(clientAddress);

        // Get all the clientAddressList
        restClientAddressMockMvc.perform(get("/api/client-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAddress.getId())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)));
    }
    
    @Test
    public void getClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.save(clientAddress);

        // Get the clientAddress
        restClientAddressMockMvc.perform(get("/api/client-addresses/{id}", clientAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientAddress.getId()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE));
    }

    @Test
    public void getNonExistingClientAddress() throws Exception {
        // Get the clientAddress
        restClientAddressMockMvc.perform(get("/api/client-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.save(clientAddress);

        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Update the clientAddress
        ClientAddress updatedClientAddress = clientAddressRepository.findById(clientAddress.getId()).get();
        updatedClientAddress
            .clientId(UPDATED_CLIENT_ID)
            .address(UPDATED_ADDRESS)
            .city(UPDATED_CITY)
            .postalCode(UPDATED_POSTAL_CODE);

        restClientAddressMockMvc.perform(put("/api/client-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientAddress)))
            .andExpect(status().isOk());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);
        ClientAddress testClientAddress = clientAddressList.get(clientAddressList.size() - 1);
        assertThat(testClientAddress.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testClientAddress.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testClientAddress.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testClientAddress.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);

        // Validate the ClientAddress in Elasticsearch
        verify(mockClientAddressSearchRepository, times(1)).save(testClientAddress);
    }

    @Test
    public void updateNonExistingClientAddress() throws Exception {
        int databaseSizeBeforeUpdate = clientAddressRepository.findAll().size();

        // Create the ClientAddress

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientAddressMockMvc.perform(put("/api/client-addresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientAddress)))
            .andExpect(status().isBadRequest());

        // Validate the ClientAddress in the database
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClientAddress in Elasticsearch
        verify(mockClientAddressSearchRepository, times(0)).save(clientAddress);
    }

    @Test
    public void deleteClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.save(clientAddress);

        int databaseSizeBeforeDelete = clientAddressRepository.findAll().size();

        // Get the clientAddress
        restClientAddressMockMvc.perform(delete("/api/client-addresses/{id}", clientAddress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClientAddress> clientAddressList = clientAddressRepository.findAll();
        assertThat(clientAddressList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClientAddress in Elasticsearch
        verify(mockClientAddressSearchRepository, times(1)).deleteById(clientAddress.getId());
    }

    @Test
    public void searchClientAddress() throws Exception {
        // Initialize the database
        clientAddressRepository.save(clientAddress);
        when(mockClientAddressSearchRepository.search(queryStringQuery("id:" + clientAddress.getId())))
            .thenReturn(Collections.singletonList(clientAddress));
        // Search the clientAddress
        restClientAddressMockMvc.perform(get("/api/_search/client-addresses?query=id:" + clientAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientAddress.getId())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientAddress.class);
        ClientAddress clientAddress1 = new ClientAddress();
        clientAddress1.setId("id1");
        ClientAddress clientAddress2 = new ClientAddress();
        clientAddress2.setId(clientAddress1.getId());
        assertThat(clientAddress1).isEqualTo(clientAddress2);
        clientAddress2.setId("id2");
        assertThat(clientAddress1).isNotEqualTo(clientAddress2);
        clientAddress1.setId(null);
        assertThat(clientAddress1).isNotEqualTo(clientAddress2);
    }
}
