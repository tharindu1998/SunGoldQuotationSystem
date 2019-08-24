package com.heavenscode.sungoldsolar.web.rest;

import com.heavenscode.sungoldsolar.SunGoldSolarApp;

import com.heavenscode.sungoldsolar.domain.Clients;
import com.heavenscode.sungoldsolar.repository.ClientsRepository;
import com.heavenscode.sungoldsolar.repository.search.ClientsSearchRepository;
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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;


import static com.heavenscode.sungoldsolar.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.heavenscode.sungoldsolar.domain.enumeration.Designation;
/**
 * Test class for the ClientsResource REST controller.
 *
 * @see ClientsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunGoldSolarApp.class)
public class ClientsResourceIntTest {

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Designation DEFAULT_DESIGNATION = Designation.Ms;
    private static final Designation UPDATED_DESIGNATION = Designation.Mr;

    private static final String DEFAULT_NIC_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NIC_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_TEL = "AAAAAAAAAA";
    private static final String UPDATED_TEL = "BBBBBBBBBB";

    @Autowired
    private ClientsRepository clientsRepository;

    /**
     * This repository is mocked in the com.heavenscode.sungoldsolar.repository.search test package.
     *
     * @see com.heavenscode.sungoldsolar.repository.search.ClientsSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClientsSearchRepository mockClientsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restClientsMockMvc;

    private Clients clients;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientsResource clientsResource = new ClientsResource(clientsRepository, mockClientsSearchRepository);
        this.restClientsMockMvc = MockMvcBuilders.standaloneSetup(clientsResource)
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
    public static Clients createEntity() {
        Clients clients = new Clients()
            .created(DEFAULT_CREATED)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .designation(DEFAULT_DESIGNATION)
            .nicNumber(DEFAULT_NIC_NUMBER)
            .tel(DEFAULT_TEL);
        return clients;
    }

    @Before
    public void initTest() {
        clientsRepository.deleteAll();
        clients = createEntity();
    }

    @Test
    public void createClients() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isCreated());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate + 1);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testClients.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testClients.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testClients.getDesignation()).isEqualTo(DEFAULT_DESIGNATION);
        assertThat(testClients.getNicNumber()).isEqualTo(DEFAULT_NIC_NUMBER);
        assertThat(testClients.getTel()).isEqualTo(DEFAULT_TEL);

        // Validate the Clients in Elasticsearch
        verify(mockClientsSearchRepository, times(1)).save(testClients);
    }

    @Test
    public void createClientsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsRepository.findAll().size();

        // Create the Clients with an existing ID
        clients.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsMockMvc.perform(post("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Clients in Elasticsearch
        verify(mockClientsSearchRepository, times(0)).save(clients);
    }

    @Test
    public void getAllClients() throws Exception {
        // Initialize the database
        clientsRepository.save(clients);

        // Get all the clientsList
        restClientsMockMvc.perform(get("/api/clients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].nicNumber").value(hasItem(DEFAULT_NIC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }
    
    @Test
    public void getClients() throws Exception {
        // Initialize the database
        clientsRepository.save(clients);

        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clients.getId()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME.toString()))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME.toString()))
            .andExpect(jsonPath("$.designation").value(DEFAULT_DESIGNATION.toString()))
            .andExpect(jsonPath("$.nicNumber").value(DEFAULT_NIC_NUMBER.toString()))
            .andExpect(jsonPath("$.tel").value(DEFAULT_TEL.toString()));
    }

    @Test
    public void getNonExistingClients() throws Exception {
        // Get the clients
        restClientsMockMvc.perform(get("/api/clients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateClients() throws Exception {
        // Initialize the database
        clientsRepository.save(clients);

        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Update the clients
        Clients updatedClients = clientsRepository.findById(clients.getId()).get();
        updatedClients
            .created(UPDATED_CREATED)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .designation(UPDATED_DESIGNATION)
            .nicNumber(UPDATED_NIC_NUMBER)
            .tel(UPDATED_TEL);

        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClients)))
            .andExpect(status().isOk());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);
        Clients testClients = clientsList.get(clientsList.size() - 1);
        assertThat(testClients.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testClients.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testClients.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testClients.getDesignation()).isEqualTo(UPDATED_DESIGNATION);
        assertThat(testClients.getNicNumber()).isEqualTo(UPDATED_NIC_NUMBER);
        assertThat(testClients.getTel()).isEqualTo(UPDATED_TEL);

        // Validate the Clients in Elasticsearch
        verify(mockClientsSearchRepository, times(1)).save(testClients);
    }

    @Test
    public void updateNonExistingClients() throws Exception {
        int databaseSizeBeforeUpdate = clientsRepository.findAll().size();

        // Create the Clients

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsMockMvc.perform(put("/api/clients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clients)))
            .andExpect(status().isBadRequest());

        // Validate the Clients in the database
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clients in Elasticsearch
        verify(mockClientsSearchRepository, times(0)).save(clients);
    }

    @Test
    public void deleteClients() throws Exception {
        // Initialize the database
        clientsRepository.save(clients);

        int databaseSizeBeforeDelete = clientsRepository.findAll().size();

        // Get the clients
        restClientsMockMvc.perform(delete("/api/clients/{id}", clients.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Clients> clientsList = clientsRepository.findAll();
        assertThat(clientsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Clients in Elasticsearch
        verify(mockClientsSearchRepository, times(1)).deleteById(clients.getId());
    }

    @Test
    public void searchClients() throws Exception {
        // Initialize the database
        clientsRepository.save(clients);
        when(mockClientsSearchRepository.search(queryStringQuery("id:" + clients.getId())))
            .thenReturn(Collections.singletonList(clients));
        // Search the clients
        restClientsMockMvc.perform(get("/api/_search/clients?query=id:" + clients.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clients.getId())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME.toString())))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME.toString())))
            .andExpect(jsonPath("$.[*].designation").value(hasItem(DEFAULT_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].nicNumber").value(hasItem(DEFAULT_NIC_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].tel").value(hasItem(DEFAULT_TEL.toString())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clients.class);
        Clients clients1 = new Clients();
        clients1.setId("id1");
        Clients clients2 = new Clients();
        clients2.setId(clients1.getId());
        assertThat(clients1).isEqualTo(clients2);
        clients2.setId("id2");
        assertThat(clients1).isNotEqualTo(clients2);
        clients1.setId(null);
        assertThat(clients1).isNotEqualTo(clients2);
    }
}
