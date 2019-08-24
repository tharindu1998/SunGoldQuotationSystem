package com.heavenscode.sungoldsolar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.heavenscode.sungoldsolar.domain.Quotation;
import com.heavenscode.sungoldsolar.repository.QuotationRepository;
import com.heavenscode.sungoldsolar.repository.search.QuotationSearchRepository;
import com.heavenscode.sungoldsolar.web.rest.errors.BadRequestAlertException;
import com.heavenscode.sungoldsolar.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Quotation.
 */
@RestController
@RequestMapping("/api")
public class QuotationResource {
    @Autowired
    MongoTemplate mongoTemplate;


    private final Logger log = LoggerFactory.getLogger(QuotationResource.class);

    private static final String ENTITY_NAME = "quotation";

    private final QuotationRepository quotationRepository;

    private final QuotationSearchRepository quotationSearchRepository;

    public QuotationResource(QuotationRepository quotationRepository, QuotationSearchRepository quotationSearchRepository) {
        this.quotationRepository = quotationRepository;
        this.quotationSearchRepository = quotationSearchRepository;
    }

    /**
     * POST  /quotations : Create a new quotation.
     *
     * @param quotation the quotation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new quotation, or with status 400 (Bad Request) if the quotation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/quotations")
    @Timed
    public ResponseEntity<Quotation> createQuotation(@RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to save Quotation : {}", quotation);
        if (quotation.getId() != null) {
            throw new BadRequestAlertException("A new quotation cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "indexId"));
        try {
            Optional<Quotation> quotation1 = Optional.ofNullable(mongoTemplate.find(query, Quotation.class).get(0));

            if (quotation1.isPresent()) {
                if (quotation1.get().getIndexId() != null) {
                    quotation.setIndexId(quotation1.get().getIndexId() + 1);
                } else {
                    quotation.setIndexId(1);
                }

            } else {
                quotation.setIndexId(1);
            }
        } catch (Exception e) {
            quotation.setIndexId(1);
            e.printStackTrace();
        }


        Quotation result = quotationRepository.save(quotation);
        quotationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quotations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotations : Updates an existing quotation.
     *
     * @param quotation the quotation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated quotation,
     * or with status 400 (Bad Request) if the quotation is not valid,
     * or with status 500 (Internal Server Error) if the quotation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/quotations")
    @Timed
    public ResponseEntity<Quotation> updateQuotation(@RequestBody Quotation quotation) throws URISyntaxException {
        log.debug("REST request to update Quotation : {}", quotation);
        if (quotation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Quotation result = quotationRepository.save(quotation);
        quotationSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, quotation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotations : get all the quotations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of quotations in body
     */
    @GetMapping("/quotations")
    @Timed
    public List<Quotation> getAllQuotations() {
        log.debug("REST request to get all Quotations");
        return quotationRepository.findAll();
    }

    /**
     * GET  /quotations/:id : get the "id" quotation.
     *
     * @param id the id of the quotation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the quotation, or with status 404 (Not Found)
     */
    @GetMapping("/quotations/{id}")
    @Timed
    public ResponseEntity<Quotation> getQuotation(@PathVariable String id) {
        log.debug("REST request to get Quotation : {}", id);
        Optional<Quotation> quotation = quotationRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(quotation);
    }

    /**
     * DELETE  /quotations/:id : delete the "id" quotation.
     *
     * @param id the id of the quotation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/quotations/{id}")
    @Timed
    public ResponseEntity<Void> deleteQuotation(@PathVariable String id) {
        log.debug("REST request to delete Quotation : {}", id);

        quotationRepository.deleteById(id);
        quotationSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/quotations?query=:query : search for the quotation corresponding
     * to the query.
     *
     * @param query the query of the quotation search
     * @return the result of the search
     */
    @GetMapping("/_search/quotations")
    @Timed
    public List<Quotation> searchQuotations(@RequestParam String query) {
        log.debug("REST request to search Quotations for query {}", query);
        return StreamSupport
            .stream(quotationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @GetMapping("/latest/quotation/id")
    @Timed
    public Quotation getLatestRecordId() {
        Query query = new Query();
        query.limit(1);
        query.with(new Sort(Sort.Direction.DESC, "indexId"));
        return mongoTemplate.find(query, Quotation.class).get(0);
    }


    @GetMapping("/quotations/byaddress/{adid}")
    @Timed
    public List<Quotation> getAllQuotationsByAddressId(@PathVariable String adid) {
        log.debug("REST request to get all Quotations by address id");
        return quotationRepository.findByAddressId(adid);
    }


}
