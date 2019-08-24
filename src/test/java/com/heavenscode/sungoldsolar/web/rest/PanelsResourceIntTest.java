package com.heavenscode.sungoldsolar.web.rest;

import com.heavenscode.sungoldsolar.SunGoldSolarApp;

import com.heavenscode.sungoldsolar.domain.Panels;
import com.heavenscode.sungoldsolar.repository.PanelsRepository;
import com.heavenscode.sungoldsolar.repository.search.PanelsSearchRepository;
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
 * Test class for the PanelsResource REST controller.
 *
 * @see PanelsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunGoldSolarApp.class)
public class PanelsResourceIntTest {

    private static final String DEFAULT_PALEN_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_PALEN_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_PANEL_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_PANEL_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_PANEL_SIZE = 1D;
    private static final Double UPDATED_PANEL_SIZE = 2D;

    private static final Double DEFAULT_PANEL_PRICE = 1D;
    private static final Double UPDATED_PANEL_PRICE = 2D;

    @Autowired
    private PanelsRepository panelsRepository;

    /**
     * This repository is mocked in the com.heavenscode.sungoldsolar.repository.search test package.
     *
     * @see com.heavenscode.sungoldsolar.repository.search.PanelsSearchRepositoryMockConfiguration
     */
    @Autowired
    private PanelsSearchRepository mockPanelsSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restPanelsMockMvc;

    private Panels panels;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PanelsResource panelsResource = new PanelsResource(panelsRepository, mockPanelsSearchRepository);
        this.restPanelsMockMvc = MockMvcBuilders.standaloneSetup(panelsResource)
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
    public static Panels createEntity() {
        Panels panels = new Panels()
            .palenBrand(DEFAULT_PALEN_BRAND)
            .panelModel(DEFAULT_PANEL_MODEL)
            .panelSize(DEFAULT_PANEL_SIZE)
            .panelPrice(DEFAULT_PANEL_PRICE);
        return panels;
    }

    @Before
    public void initTest() {
        panelsRepository.deleteAll();
        panels = createEntity();
    }

    @Test
    public void createPanels() throws Exception {
        int databaseSizeBeforeCreate = panelsRepository.findAll().size();

        // Create the Panels
        restPanelsMockMvc.perform(post("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panels)))
            .andExpect(status().isCreated());

        // Validate the Panels in the database
        List<Panels> panelsList = panelsRepository.findAll();
        assertThat(panelsList).hasSize(databaseSizeBeforeCreate + 1);
        Panels testPanels = panelsList.get(panelsList.size() - 1);
        assertThat(testPanels.getPalenBrand()).isEqualTo(DEFAULT_PALEN_BRAND);
        assertThat(testPanels.getPanelModel()).isEqualTo(DEFAULT_PANEL_MODEL);
        assertThat(testPanels.getPanelSize()).isEqualTo(DEFAULT_PANEL_SIZE);
        assertThat(testPanels.getPanelPrice()).isEqualTo(DEFAULT_PANEL_PRICE);

        // Validate the Panels in Elasticsearch
        verify(mockPanelsSearchRepository, times(1)).save(testPanels);
    }

    @Test
    public void createPanelsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = panelsRepository.findAll().size();

        // Create the Panels with an existing ID
        panels.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restPanelsMockMvc.perform(post("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panels)))
            .andExpect(status().isBadRequest());

        // Validate the Panels in the database
        List<Panels> panelsList = panelsRepository.findAll();
        assertThat(panelsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Panels in Elasticsearch
        verify(mockPanelsSearchRepository, times(0)).save(panels);
    }

    @Test
    public void getAllPanels() throws Exception {
        // Initialize the database
        panelsRepository.save(panels);

        // Get all the panelsList
        restPanelsMockMvc.perform(get("/api/panels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panels.getId())))
            .andExpect(jsonPath("$.[*].palenBrand").value(hasItem(DEFAULT_PALEN_BRAND.toString())))
            .andExpect(jsonPath("$.[*].panelModel").value(hasItem(DEFAULT_PANEL_MODEL.toString())))
            .andExpect(jsonPath("$.[*].panelSize").value(hasItem(DEFAULT_PANEL_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].panelPrice").value(hasItem(DEFAULT_PANEL_PRICE.doubleValue())));
    }
    
    @Test
    public void getPanels() throws Exception {
        // Initialize the database
        panelsRepository.save(panels);

        // Get the panels
        restPanelsMockMvc.perform(get("/api/panels/{id}", panels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(panels.getId()))
            .andExpect(jsonPath("$.palenBrand").value(DEFAULT_PALEN_BRAND.toString()))
            .andExpect(jsonPath("$.panelModel").value(DEFAULT_PANEL_MODEL.toString()))
            .andExpect(jsonPath("$.panelSize").value(DEFAULT_PANEL_SIZE.doubleValue()))
            .andExpect(jsonPath("$.panelPrice").value(DEFAULT_PANEL_PRICE.doubleValue()));
    }

    @Test
    public void getNonExistingPanels() throws Exception {
        // Get the panels
        restPanelsMockMvc.perform(get("/api/panels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePanels() throws Exception {
        // Initialize the database
        panelsRepository.save(panels);

        int databaseSizeBeforeUpdate = panelsRepository.findAll().size();

        // Update the panels
        Panels updatedPanels = panelsRepository.findById(panels.getId()).get();
        updatedPanels
            .palenBrand(UPDATED_PALEN_BRAND)
            .panelModel(UPDATED_PANEL_MODEL)
            .panelSize(UPDATED_PANEL_SIZE)
            .panelPrice(UPDATED_PANEL_PRICE);

        restPanelsMockMvc.perform(put("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPanels)))
            .andExpect(status().isOk());

        // Validate the Panels in the database
        List<Panels> panelsList = panelsRepository.findAll();
        assertThat(panelsList).hasSize(databaseSizeBeforeUpdate);
        Panels testPanels = panelsList.get(panelsList.size() - 1);
        assertThat(testPanels.getPalenBrand()).isEqualTo(UPDATED_PALEN_BRAND);
        assertThat(testPanels.getPanelModel()).isEqualTo(UPDATED_PANEL_MODEL);
        assertThat(testPanels.getPanelSize()).isEqualTo(UPDATED_PANEL_SIZE);
        assertThat(testPanels.getPanelPrice()).isEqualTo(UPDATED_PANEL_PRICE);

        // Validate the Panels in Elasticsearch
        verify(mockPanelsSearchRepository, times(1)).save(testPanels);
    }

    @Test
    public void updateNonExistingPanels() throws Exception {
        int databaseSizeBeforeUpdate = panelsRepository.findAll().size();

        // Create the Panels

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPanelsMockMvc.perform(put("/api/panels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(panels)))
            .andExpect(status().isBadRequest());

        // Validate the Panels in the database
        List<Panels> panelsList = panelsRepository.findAll();
        assertThat(panelsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Panels in Elasticsearch
        verify(mockPanelsSearchRepository, times(0)).save(panels);
    }

    @Test
    public void deletePanels() throws Exception {
        // Initialize the database
        panelsRepository.save(panels);

        int databaseSizeBeforeDelete = panelsRepository.findAll().size();

        // Get the panels
        restPanelsMockMvc.perform(delete("/api/panels/{id}", panels.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Panels> panelsList = panelsRepository.findAll();
        assertThat(panelsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Panels in Elasticsearch
        verify(mockPanelsSearchRepository, times(1)).deleteById(panels.getId());
    }

    @Test
    public void searchPanels() throws Exception {
        // Initialize the database
        panelsRepository.save(panels);
        when(mockPanelsSearchRepository.search(queryStringQuery("id:" + panels.getId())))
            .thenReturn(Collections.singletonList(panels));
        // Search the panels
        restPanelsMockMvc.perform(get("/api/_search/panels?query=id:" + panels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(panels.getId())))
            .andExpect(jsonPath("$.[*].palenBrand").value(hasItem(DEFAULT_PALEN_BRAND.toString())))
            .andExpect(jsonPath("$.[*].panelModel").value(hasItem(DEFAULT_PANEL_MODEL.toString())))
            .andExpect(jsonPath("$.[*].panelSize").value(hasItem(DEFAULT_PANEL_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].panelPrice").value(hasItem(DEFAULT_PANEL_PRICE.doubleValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Panels.class);
        Panels panels1 = new Panels();
        panels1.setId("id1");
        Panels panels2 = new Panels();
        panels2.setId(panels1.getId());
        assertThat(panels1).isEqualTo(panels2);
        panels2.setId("id2");
        assertThat(panels1).isNotEqualTo(panels2);
        panels1.setId(null);
        assertThat(panels1).isNotEqualTo(panels2);
    }
}
