package com.heavenscode.sungoldsolar.web.rest;

import com.heavenscode.sungoldsolar.SunGoldSolarApp;

import com.heavenscode.sungoldsolar.domain.Quotation;
import com.heavenscode.sungoldsolar.repository.QuotationRepository;
import com.heavenscode.sungoldsolar.repository.search.QuotationSearchRepository;
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

/**
 * Test class for the QuotationResource REST controller.
 *
 * @see QuotationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SunGoldSolarApp.class)
public class QuotationResourceIntTest {

    private static final String DEFAULT_CLIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_ID = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_SYSTEM_SIZE = 1D;
    private static final Double UPDATED_SYSTEM_SIZE = 2D;

    private static final String DEFAULT_UNIT_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_USAGE = "BBBBBBBBBB";

    private static final Double DEFAULT_UNITS_GENERATES = 1D;
    private static final Double UPDATED_UNITS_GENERATES = 2D;

    private static final String DEFAULT_PANEL_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_PANEL_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_PANEL_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_PANEL_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_NUMBEROF_PANELS = 1D;
    private static final Double UPDATED_NUMBEROF_PANELS = 2D;

    private static final Double DEFAULT_PANEL_CAPACITY = 1D;
    private static final Double UPDATED_PANEL_CAPACITY = 2D;

    private static final String DEFAULT_INVERTER_BRAND = "AAAAAAAAAA";
    private static final String UPDATED_INVERTER_BRAND = "BBBBBBBBBB";

    private static final String DEFAULT_INVERTER_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_INVERTER_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_INVERTER_CAPACITY = 1D;
    private static final Double UPDATED_INVERTER_CAPACITY = 2D;

    private static final Double DEFAULT_AREA_NEEDED = 1D;
    private static final Double UPDATED_AREA_NEEDED = 2D;

    private static final String DEFAULT_MOUNTING_STRUCTURE = "AAAAAAAAAA";
    private static final String UPDATED_MOUNTING_STRUCTURE = "BBBBBBBBBB";

    private static final String DEFAULT_CONSTRUCTION_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_CONSTRUCTION_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_PROFIT = "AAAAAAAAAA";
    private static final String UPDATED_PROFIT = "BBBBBBBBBB";

    private static final String DEFAULT_COMMISSION = "AAAAAAAAAA";
    private static final String UPDATED_COMMISSION = "BBBBBBBBBB";

    private static final Double DEFAULT_SYSTEM_PRICE = 1D;
    private static final Double UPDATED_SYSTEM_PRICE = 2D;

    @Autowired
    private QuotationRepository quotationRepository;

    /**
     * This repository is mocked in the com.heavenscode.sungoldsolar.repository.search test package.
     *
     * @see com.heavenscode.sungoldsolar.repository.search.QuotationSearchRepositoryMockConfiguration
     */
    @Autowired
    private QuotationSearchRepository mockQuotationSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restQuotationMockMvc;

    private Quotation quotation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuotationResource quotationResource = new QuotationResource(quotationRepository, mockQuotationSearchRepository);
        this.restQuotationMockMvc = MockMvcBuilders.standaloneSetup(quotationResource)
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
    public static Quotation createEntity() {
        Quotation quotation = new Quotation()
            .clientId(DEFAULT_CLIENT_ID)
            .addressId(DEFAULT_ADDRESS_ID)
            .created(DEFAULT_CREATED)
            .systemSize(DEFAULT_SYSTEM_SIZE)
            .unitUsage(DEFAULT_UNIT_USAGE)
            .unitsGenerates(DEFAULT_UNITS_GENERATES)
            .panelBrand(DEFAULT_PANEL_BRAND)
            .panelModel(DEFAULT_PANEL_MODEL)
            .numberofPanels(DEFAULT_NUMBEROF_PANELS)
            .panelCapacity(DEFAULT_PANEL_CAPACITY)
            .inverterBrand(DEFAULT_INVERTER_BRAND)
            .inverterModel(DEFAULT_INVERTER_MODEL)
            .inverterCapacity(DEFAULT_INVERTER_CAPACITY)
            .areaNeeded(DEFAULT_AREA_NEEDED)
            .mountingStructure(DEFAULT_MOUNTING_STRUCTURE)
            .constructionPrice(DEFAULT_CONSTRUCTION_PRICE)
            .profit(DEFAULT_PROFIT)
            .commission(DEFAULT_COMMISSION)
            .systemPrice(DEFAULT_SYSTEM_PRICE);
        return quotation;
    }

    @Before
    public void initTest() {
        quotationRepository.deleteAll();
        quotation = createEntity();
    }

    @Test
    public void createQuotation() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isCreated());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate + 1);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getClientId()).isEqualTo(DEFAULT_CLIENT_ID);
        assertThat(testQuotation.getAddressId()).isEqualTo(DEFAULT_ADDRESS_ID);
        assertThat(testQuotation.getCreated()).isEqualTo(DEFAULT_CREATED);
        assertThat(testQuotation.getSystemSize()).isEqualTo(DEFAULT_SYSTEM_SIZE);
        assertThat(testQuotation.getUnitUsage()).isEqualTo(DEFAULT_UNIT_USAGE);
        assertThat(testQuotation.getUnitsGenerates()).isEqualTo(DEFAULT_UNITS_GENERATES);
        assertThat(testQuotation.getPanelBrand()).isEqualTo(DEFAULT_PANEL_BRAND);
        assertThat(testQuotation.getPanelModel()).isEqualTo(DEFAULT_PANEL_MODEL);
        assertThat(testQuotation.getNumberofPanels()).isEqualTo(DEFAULT_NUMBEROF_PANELS);
        assertThat(testQuotation.getPanelCapacity()).isEqualTo(DEFAULT_PANEL_CAPACITY);
        assertThat(testQuotation.getInverterBrand()).isEqualTo(DEFAULT_INVERTER_BRAND);
        assertThat(testQuotation.getInverterModel()).isEqualTo(DEFAULT_INVERTER_MODEL);
        assertThat(testQuotation.getInverterCapacity()).isEqualTo(DEFAULT_INVERTER_CAPACITY);
        assertThat(testQuotation.getAreaNeeded()).isEqualTo(DEFAULT_AREA_NEEDED);
        assertThat(testQuotation.getMountingStructure()).isEqualTo(DEFAULT_MOUNTING_STRUCTURE);
        assertThat(testQuotation.getConstructionPrice()).isEqualTo(DEFAULT_CONSTRUCTION_PRICE);
        assertThat(testQuotation.getProfit()).isEqualTo(DEFAULT_PROFIT);
        assertThat(testQuotation.getCommission()).isEqualTo(DEFAULT_COMMISSION);
        assertThat(testQuotation.getSystemPrice()).isEqualTo(DEFAULT_SYSTEM_PRICE);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    public void createQuotationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = quotationRepository.findAll().size();

        // Create the Quotation with an existing ID
        quotation.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuotationMockMvc.perform(post("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeCreate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    public void getAllQuotations() throws Exception {
        // Initialize the database
        quotationRepository.save(quotation);

        // Get all the quotationList
        restQuotationMockMvc.perform(get("/api/quotations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].systemSize").value(hasItem(DEFAULT_SYSTEM_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitUsage").value(hasItem(DEFAULT_UNIT_USAGE.toString())))
            .andExpect(jsonPath("$.[*].unitsGenerates").value(hasItem(DEFAULT_UNITS_GENERATES.doubleValue())))
            .andExpect(jsonPath("$.[*].panelBrand").value(hasItem(DEFAULT_PANEL_BRAND.toString())))
            .andExpect(jsonPath("$.[*].panelModel").value(hasItem(DEFAULT_PANEL_MODEL.toString())))
            .andExpect(jsonPath("$.[*].numberofPanels").value(hasItem(DEFAULT_NUMBEROF_PANELS.doubleValue())))
            .andExpect(jsonPath("$.[*].panelCapacity").value(hasItem(DEFAULT_PANEL_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].inverterBrand").value(hasItem(DEFAULT_INVERTER_BRAND.toString())))
            .andExpect(jsonPath("$.[*].inverterModel").value(hasItem(DEFAULT_INVERTER_MODEL.toString())))
            .andExpect(jsonPath("$.[*].inverterCapacity").value(hasItem(DEFAULT_INVERTER_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].areaNeeded").value(hasItem(DEFAULT_AREA_NEEDED.doubleValue())))
            .andExpect(jsonPath("$.[*].mountingStructure").value(hasItem(DEFAULT_MOUNTING_STRUCTURE.toString())))
            .andExpect(jsonPath("$.[*].constructionPrice").value(hasItem(DEFAULT_CONSTRUCTION_PRICE.toString())))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.toString())))
            .andExpect(jsonPath("$.[*].systemPrice").value(hasItem(DEFAULT_SYSTEM_PRICE.doubleValue())));
    }
    
    @Test
    public void getQuotation() throws Exception {
        // Initialize the database
        quotationRepository.save(quotation);

        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(quotation.getId()))
            .andExpect(jsonPath("$.clientId").value(DEFAULT_CLIENT_ID.toString()))
            .andExpect(jsonPath("$.addressId").value(DEFAULT_ADDRESS_ID.toString()))
            .andExpect(jsonPath("$.created").value(DEFAULT_CREATED.toString()))
            .andExpect(jsonPath("$.systemSize").value(DEFAULT_SYSTEM_SIZE.doubleValue()))
            .andExpect(jsonPath("$.unitUsage").value(DEFAULT_UNIT_USAGE.toString()))
            .andExpect(jsonPath("$.unitsGenerates").value(DEFAULT_UNITS_GENERATES.doubleValue()))
            .andExpect(jsonPath("$.panelBrand").value(DEFAULT_PANEL_BRAND.toString()))
            .andExpect(jsonPath("$.panelModel").value(DEFAULT_PANEL_MODEL.toString()))
            .andExpect(jsonPath("$.numberofPanels").value(DEFAULT_NUMBEROF_PANELS.doubleValue()))
            .andExpect(jsonPath("$.panelCapacity").value(DEFAULT_PANEL_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.inverterBrand").value(DEFAULT_INVERTER_BRAND.toString()))
            .andExpect(jsonPath("$.inverterModel").value(DEFAULT_INVERTER_MODEL.toString()))
            .andExpect(jsonPath("$.inverterCapacity").value(DEFAULT_INVERTER_CAPACITY.doubleValue()))
            .andExpect(jsonPath("$.areaNeeded").value(DEFAULT_AREA_NEEDED.doubleValue()))
            .andExpect(jsonPath("$.mountingStructure").value(DEFAULT_MOUNTING_STRUCTURE.toString()))
            .andExpect(jsonPath("$.constructionPrice").value(DEFAULT_CONSTRUCTION_PRICE.toString()))
            .andExpect(jsonPath("$.profit").value(DEFAULT_PROFIT.toString()))
            .andExpect(jsonPath("$.commission").value(DEFAULT_COMMISSION.toString()))
            .andExpect(jsonPath("$.systemPrice").value(DEFAULT_SYSTEM_PRICE.doubleValue()));
    }

    @Test
    public void getNonExistingQuotation() throws Exception {
        // Get the quotation
        restQuotationMockMvc.perform(get("/api/quotations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateQuotation() throws Exception {
        // Initialize the database
        quotationRepository.save(quotation);

        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Update the quotation
        Quotation updatedQuotation = quotationRepository.findById(quotation.getId()).get();
        updatedQuotation
            .clientId(UPDATED_CLIENT_ID)
            .addressId(UPDATED_ADDRESS_ID)
            .created(UPDATED_CREATED)
            .systemSize(UPDATED_SYSTEM_SIZE)
            .unitUsage(UPDATED_UNIT_USAGE)
            .unitsGenerates(UPDATED_UNITS_GENERATES)
            .panelBrand(UPDATED_PANEL_BRAND)
            .panelModel(UPDATED_PANEL_MODEL)
            .numberofPanels(UPDATED_NUMBEROF_PANELS)
            .panelCapacity(UPDATED_PANEL_CAPACITY)
            .inverterBrand(UPDATED_INVERTER_BRAND)
            .inverterModel(UPDATED_INVERTER_MODEL)
            .inverterCapacity(UPDATED_INVERTER_CAPACITY)
            .areaNeeded(UPDATED_AREA_NEEDED)
            .mountingStructure(UPDATED_MOUNTING_STRUCTURE)
            .constructionPrice(UPDATED_CONSTRUCTION_PRICE)
            .profit(UPDATED_PROFIT)
            .commission(UPDATED_COMMISSION)
            .systemPrice(UPDATED_SYSTEM_PRICE);

        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuotation)))
            .andExpect(status().isOk());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);
        Quotation testQuotation = quotationList.get(quotationList.size() - 1);
        assertThat(testQuotation.getClientId()).isEqualTo(UPDATED_CLIENT_ID);
        assertThat(testQuotation.getAddressId()).isEqualTo(UPDATED_ADDRESS_ID);
        assertThat(testQuotation.getCreated()).isEqualTo(UPDATED_CREATED);
        assertThat(testQuotation.getSystemSize()).isEqualTo(UPDATED_SYSTEM_SIZE);
        assertThat(testQuotation.getUnitUsage()).isEqualTo(UPDATED_UNIT_USAGE);
        assertThat(testQuotation.getUnitsGenerates()).isEqualTo(UPDATED_UNITS_GENERATES);
        assertThat(testQuotation.getPanelBrand()).isEqualTo(UPDATED_PANEL_BRAND);
        assertThat(testQuotation.getPanelModel()).isEqualTo(UPDATED_PANEL_MODEL);
        assertThat(testQuotation.getNumberofPanels()).isEqualTo(UPDATED_NUMBEROF_PANELS);
        assertThat(testQuotation.getPanelCapacity()).isEqualTo(UPDATED_PANEL_CAPACITY);
        assertThat(testQuotation.getInverterBrand()).isEqualTo(UPDATED_INVERTER_BRAND);
        assertThat(testQuotation.getInverterModel()).isEqualTo(UPDATED_INVERTER_MODEL);
        assertThat(testQuotation.getInverterCapacity()).isEqualTo(UPDATED_INVERTER_CAPACITY);
        assertThat(testQuotation.getAreaNeeded()).isEqualTo(UPDATED_AREA_NEEDED);
        assertThat(testQuotation.getMountingStructure()).isEqualTo(UPDATED_MOUNTING_STRUCTURE);
        assertThat(testQuotation.getConstructionPrice()).isEqualTo(UPDATED_CONSTRUCTION_PRICE);
        assertThat(testQuotation.getProfit()).isEqualTo(UPDATED_PROFIT);
        assertThat(testQuotation.getCommission()).isEqualTo(UPDATED_COMMISSION);
        assertThat(testQuotation.getSystemPrice()).isEqualTo(UPDATED_SYSTEM_PRICE);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).save(testQuotation);
    }

    @Test
    public void updateNonExistingQuotation() throws Exception {
        int databaseSizeBeforeUpdate = quotationRepository.findAll().size();

        // Create the Quotation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuotationMockMvc.perform(put("/api/quotations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(quotation)))
            .andExpect(status().isBadRequest());

        // Validate the Quotation in the database
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(0)).save(quotation);
    }

    @Test
    public void deleteQuotation() throws Exception {
        // Initialize the database
        quotationRepository.save(quotation);

        int databaseSizeBeforeDelete = quotationRepository.findAll().size();

        // Get the quotation
        restQuotationMockMvc.perform(delete("/api/quotations/{id}", quotation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Quotation> quotationList = quotationRepository.findAll();
        assertThat(quotationList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Quotation in Elasticsearch
        verify(mockQuotationSearchRepository, times(1)).deleteById(quotation.getId());
    }

    @Test
    public void searchQuotation() throws Exception {
        // Initialize the database
        quotationRepository.save(quotation);
        when(mockQuotationSearchRepository.search(queryStringQuery("id:" + quotation.getId())))
            .thenReturn(Collections.singletonList(quotation));
        // Search the quotation
        restQuotationMockMvc.perform(get("/api/_search/quotations?query=id:" + quotation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(quotation.getId())))
            .andExpect(jsonPath("$.[*].clientId").value(hasItem(DEFAULT_CLIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].addressId").value(hasItem(DEFAULT_ADDRESS_ID.toString())))
            .andExpect(jsonPath("$.[*].created").value(hasItem(DEFAULT_CREATED.toString())))
            .andExpect(jsonPath("$.[*].systemSize").value(hasItem(DEFAULT_SYSTEM_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].unitUsage").value(hasItem(DEFAULT_UNIT_USAGE.toString())))
            .andExpect(jsonPath("$.[*].unitsGenerates").value(hasItem(DEFAULT_UNITS_GENERATES.doubleValue())))
            .andExpect(jsonPath("$.[*].panelBrand").value(hasItem(DEFAULT_PANEL_BRAND.toString())))
            .andExpect(jsonPath("$.[*].panelModel").value(hasItem(DEFAULT_PANEL_MODEL.toString())))
            .andExpect(jsonPath("$.[*].numberofPanels").value(hasItem(DEFAULT_NUMBEROF_PANELS.doubleValue())))
            .andExpect(jsonPath("$.[*].panelCapacity").value(hasItem(DEFAULT_PANEL_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].inverterBrand").value(hasItem(DEFAULT_INVERTER_BRAND.toString())))
            .andExpect(jsonPath("$.[*].inverterModel").value(hasItem(DEFAULT_INVERTER_MODEL.toString())))
            .andExpect(jsonPath("$.[*].inverterCapacity").value(hasItem(DEFAULT_INVERTER_CAPACITY.doubleValue())))
            .andExpect(jsonPath("$.[*].areaNeeded").value(hasItem(DEFAULT_AREA_NEEDED.doubleValue())))
            .andExpect(jsonPath("$.[*].mountingStructure").value(hasItem(DEFAULT_MOUNTING_STRUCTURE.toString())))
            .andExpect(jsonPath("$.[*].constructionPrice").value(hasItem(DEFAULT_CONSTRUCTION_PRICE.toString())))
            .andExpect(jsonPath("$.[*].profit").value(hasItem(DEFAULT_PROFIT.toString())))
            .andExpect(jsonPath("$.[*].commission").value(hasItem(DEFAULT_COMMISSION.toString())))
            .andExpect(jsonPath("$.[*].systemPrice").value(hasItem(DEFAULT_SYSTEM_PRICE.doubleValue())));
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Quotation.class);
        Quotation quotation1 = new Quotation();
        quotation1.setId("id1");
        Quotation quotation2 = new Quotation();
        quotation2.setId(quotation1.getId());
        assertThat(quotation1).isEqualTo(quotation2);
        quotation2.setId("id2");
        assertThat(quotation1).isNotEqualTo(quotation2);
        quotation1.setId(null);
        assertThat(quotation1).isNotEqualTo(quotation2);
    }
}
