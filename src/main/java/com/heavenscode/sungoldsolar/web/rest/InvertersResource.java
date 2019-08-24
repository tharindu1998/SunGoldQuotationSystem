package com.heavenscode.sungoldsolar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.heavenscode.sungoldsolar.domain.Inverters;
import com.heavenscode.sungoldsolar.repository.InvertersRepository;
import com.heavenscode.sungoldsolar.repository.search.InvertersSearchRepository;
import com.heavenscode.sungoldsolar.web.rest.errors.BadRequestAlertException;
import com.heavenscode.sungoldsolar.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Inverters.
 */
@RestController
@RequestMapping("/api")
public class InvertersResource {

    private final Logger log = LoggerFactory.getLogger(InvertersResource.class);

    private static final String ENTITY_NAME = "inverters";

    private final InvertersRepository invertersRepository;

    private final InvertersSearchRepository invertersSearchRepository;

    public InvertersResource(InvertersRepository invertersRepository, InvertersSearchRepository invertersSearchRepository) {
        this.invertersRepository = invertersRepository;
        this.invertersSearchRepository = invertersSearchRepository;
    }

    /**
     * POST  /inverters : Create a new inverters.
     *
     * @param inverters the inverters to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inverters, or with status 400 (Bad Request) if the inverters has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inverters")
    @Timed
    public ResponseEntity<Inverters> createInverters(@RequestBody Inverters inverters) throws URISyntaxException {
        log.debug("REST request to save Inverters : {}", inverters);
        if (inverters.getId() != null) {
            throw new BadRequestAlertException("A new inverters cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inverters result = invertersRepository.save(inverters);
        invertersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/inverters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inverters : Updates an existing inverters.
     *
     * @param inverters the inverters to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inverters,
     * or with status 400 (Bad Request) if the inverters is not valid,
     * or with status 500 (Internal Server Error) if the inverters couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inverters")
    @Timed
    public ResponseEntity<Inverters> updateInverters(@RequestBody Inverters inverters) throws URISyntaxException {
        log.debug("REST request to update Inverters : {}", inverters);
        if (inverters.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Inverters result = invertersRepository.save(inverters);
        invertersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inverters.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inverters : get all the inverters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inverters in body
     */
    @GetMapping("/inverters")
    @Timed
    public List<Inverters> getAllInverters() {
        log.debug("REST request to get all Inverters");
        return invertersRepository.findAll();
    }

    /**
     * GET  /inverters/:id : get the "id" inverters.
     *
     * @param id the id of the inverters to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inverters, or with status 404 (Not Found)
     */
    @GetMapping("/inverters/{id}")
    @Timed
    public ResponseEntity<Inverters> getInverters(@PathVariable String id) {
        log.debug("REST request to get Inverters : {}", id);
        Optional<Inverters> inverters = invertersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(inverters);
    }

    /**
     * DELETE  /inverters/:id : delete the "id" inverters.
     *
     * @param id the id of the inverters to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inverters/{id}")
    @Timed
    public ResponseEntity<Void> deleteInverters(@PathVariable String id) {
        log.debug("REST request to delete Inverters : {}", id);

        invertersRepository.deleteById(id);
        invertersSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/inverters?query=:query : search for the inverters corresponding
     * to the query.
     *
     * @param query the query of the inverters search
     * @return the result of the search
     */
    @GetMapping("/_search/inverters")
    @Timed
    public List<Inverters> searchInverters(@RequestParam String query) {
        log.debug("REST request to search Inverters for query {}", query);
        return StreamSupport
            .stream(invertersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
