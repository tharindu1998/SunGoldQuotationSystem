package com.heavenscode.sungoldsolar.web.rest;

import com.heavenscode.sungoldsolar.SunGoldSolarApp;

import com.heavenscode.sungoldsolar.domain.Inverters;
import com.heavenscode.sungoldsolar.repository.InvertersRepository;
import com.heavenscode.sungoldsolar.repository.search.InvertersSearchRepository;
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
 * Test class for the InvertersResource REST controller.
 *
 * @see InvertersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunGoldSolarApp.class)
public class InvertersResourceIntTest {

    private static final String DEFAULT_INVERTER_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_INVERTER_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_INVERTER_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_INVERTER_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_INVERTER_SIZE = 1D;
    private static final Double UPDATED_INVERTER_SIZE = 2D;

    private static final Double DEFAULT_INVERTER_PRICE = 1D;
    private static final Double UPDATED_INVERTER_PRICE = 2D;

    @Autowired
    private InvertersRepository invertersRepository;

    /**
     * This repository is mocked in the com.heavenscode.sungoldsolar.repository.search test package.
     *
     * @see com.heavenscode.sungoldsolar.repository.search.InvertersSearchRepositoryMockConfiguration
     */
    @Autowired
    private InvertersSearchRepository mockInvertersSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restInvertersMockMvc;

    private Inverters inverters;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InvertersResource invertersResource = new InvertersResource(invertersRepository, mockInvertersSearchRepository);
        this.restInvertersMockMvc = MockMvcBuilders.standaloneSetup(invertersResource)
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
    public static Inverters createEntity() {
        Inverters inverters = new Inverters()
            .inverterBrand(DEFAULT_INVERTER_BRAND)
            .inverterModel(DEFAULT_INVERTER_MODEL)
            .inverterSize(DEFAULT_INVERTER_SIZE)
            .inverterPrice(DEFAULT_INVERTER_PRICE);
        return inverters;
    }

    @Before
    public void initTest() {
        invertersRepository.deleteAll();
        inverters = createEntity();
    }

    @Test
    public void createInverters() throws Exception {
        int databaseSizeBeforeCreate = invertersRepository.findAll().size();

        // Create the Inverters
        restInvertersMockMvc.perform(post("/api/inverters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inverters)))
            .andExpect(status().isCreated());

        // Validate the Inverters in the database
        List<Inverters> invertersList = invertersRepository.findAll();
        assertThat(invertersList).hasSize(databaseSizeBeforeCreate + 1);
        Inverters testInverters = invertersList.get(invertersList.size() - 1);
        assertThat(testInverters.getInverterBrand()).isEqualTo(DEFAULT_INVERTER_BRAND);
        assertThat(testInverters.getInverterModel()).isEqualTo(DEFAULT_INVERTER_MODEL);
        assertThat(testInverters.getInverterSize()).isEqualTo(DEFAULT_INVERTER_SIZE);
        assertThat(testInverters.getInverterPrice()).isEqualTo(DEFAULT_INVERTER_PRICE);

        // Validate the Inverters in Elasticsearch
        verify(mockInvertersSearchRepository, times(1)).save(testInverters);
    }

    @Test
    public void createInvertersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invertersRepository.findAll().size();

        // Create the Inverters with an existing ID
        inverters.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvertersMockMvc.perform(post("/api/inverters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inverters)))
            .andExpect(status().isBadRequest());

        // Validate the Inverters in the database
        List<Inverters> invertersList = invertersRepository.findAll();
        assertThat(invertersList).hasSize(databaseSizeBeforeCreate);

        // Validate the Inverters in Elasticsearch
        verify(mockInvertersSearchRepository, times(0)).save(inverters);
    }

    @Test
    public void getAllInverters() throws Exception {
        // Initialize the database
        invertersRepository.save(inverters);

        // Get all the invertersList
        restInvertersMockMvc.perform(get("/api/inverters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inverters.getId())))
            .andExpect(jsonPath("$.[*].inverterBrand").value(hasItem(DEFAULT_INVERTER_BRAND.toString())))
            .andExpect(jsonPath("$.[*].inverterModel").value(hasItem(DEFAULT_INVERTER_MODEL.toString())))
            .andExpect(jsonPath("$.[*].inverterSize").value(hasItem(DEFAULT_INVERTER_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].inverterPrice").value(hasItem(DEFAULT_INVERTER_PRICE.doubleValue())));
    }
    
    @Test
    public void getInverters() throws Exception {
        // Initialize the database
        invertersRepository.save(inverters);

        // Get the inverters
        restInvertersMockMvc.perform(get("/api/inverters/{id}", inverters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inverters.getId()))
            .andExpect(jsonPath("$.inverterBrand").value(DEFAULT_INVERTER_BRAND.toString()))
            .andExpect(jsonPath("$.inverterModel").value(DEFAULT_INVERTER_MODEL.toString()))
            .andExpect(jsonPath("$.inverterSize").value(DEFAULT_INVERTER_SIZE.doubleValue()))
            .andExpect(jsonPath("$.inverterPrice").value(DEFAULT_INVERTER_PRICE.doubleValue()));
    }

    @Test
    public void getNonExistingInverters() throws Exception {
        // Get the inverters
        restInvertersMockMvc.perform(get("/api/inverters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateInverters() throws Exception {
        // Initialize the database
        invertersRepository.save(inverters);

        int databaseSizeBeforeUpdate = invertersRepository.findAll().size();

        // Update the inverters
        Inverters updatedInverters = invertersRepository.findById(inverters.getId()).get();
        updatedInverters
            .inverterBrand(UPDATED_INVERTER_BRAND)
            .inverterModel(UPDATED_INVERTER_MODEL)
            .inverterSize(UPDATED_INVERTER_SIZE)
            .inverterPrice(UPDATED_INVERTER_PRICE);

        restInvertersMockMvc.perform(put("/api/inverters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInverters)))
            .andExpect(status().isOk());

        // Validate the Inverters in the database
        List<Inverters> invertersList = invertersRepository.findAll();
        assertThat(invertersList).hasSize(databaseSizeBeforeUpdate);
        Inverters testInverters = invertersList.get(invertersList.size() - 1);
        assertThat(testInverters.getInverterBrand()).isEqualTo(UPDATED_INVERTER_BRAND);
        assertThat(testInverters.getInverterModel()).isEqualTo(UPDATED_INVERTER_MODEL);
        assertThat(testInverters.getInverterSize()).isEqualTo(UPDATED_INVERTER_SIZE);
        assertThat(testInverters.getInverterPrice()).isEqualTo(UPDATED_INVERTER_PRICE);

        // Validate the Inverters in Elasticsearch
        verify(mockInvertersSearchRepository, times(1)).save(testInverters);
    }

    @Test
    public void updateNonExistingInverters() throws Exception {
        int databaseSizeBeforeUpdate = invertersRepository.findAll().size();

        // Create the Inverters

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvertersMockMvc.perform(put("/api/inverters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inverters)))
            .andExpect(status().isBadRequest());

        // Validate the Inverters in the database
        List<Inverters> invertersList = invertersRepository.findAll();
        assertThat(invertersList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Inverters in Elasticsearch
        verify(mockInvertersSearchRepository, times(0)).save(inverters);
    }

    @Test
    public void deleteInverters() throws Exception {
        // Initialize the database
        invertersRepository.save(inverters);

        int databaseSizeBeforeDelete = invertersRepository.findAll().size();

        // Get the inverters
        restInvertersMockMvc.perform(delete("/api/inverters/{id}", inverters.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inverters> invertersList = invertersRepository.findAll();
        assertThat(invertersList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Inverters in Elasticsearch
        verify(mockInvertersSearchRepository, times(1)).deleteById(inverters.getId());
    }

    @Test
    public void searchInverters() throws Exception {
        // Initialize the database
        invertersRepository.save(inverters);
        when(mockInvertersSearchRepository.search(queryStringQuery("id:" + inverters.getId())))
            .thenReturn(Collections.singletonList(inverters));
        // Search the inverters
        restInvertersMockMvc.perform(get("/api/_search/inverters?query=id:" + inverters.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inverters.getId())))
            .andExpect(jsonPath("$.[*].inverterBrand").value(hasItem(DEFAULT_INVERTER_BRAND.toString())))
            .andExpect(jsonPath("$.[*].inverterModel").value(hasItem(DEFAULT_INVERTER_MODEL.toString())))
            .andExpect(jsonPath("$.[*].inverterSize").value(hasItem(DEFAULT_INVERTER_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].inverterPrice").value(hasItem(DEFAULT_INVERTER_PRICE.doubleValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inverters.class);
        Inverters inverters1 = new Inverters();
        inverters1.setId("id1");
        Inverters inverters2 = new Inverters();
        inverters2.setId(inverters1.getId());
        assertThat(inverters1).isEqualTo(inverters2);
        inverters2.setId("id2");
        assertThat(inverters1).isNotEqualTo(inverters2);
        inverters1.setId(null);
        assertThat(inverters1).isNotEqualTo(inverters2);
    }
}
