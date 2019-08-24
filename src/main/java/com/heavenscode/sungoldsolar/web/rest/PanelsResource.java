package com.heavenscode.sungoldsolar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.heavenscode.sungoldsolar.domain.Panels;
import com.heavenscode.sungoldsolar.repository.PanelsRepository;
import com.heavenscode.sungoldsolar.repository.search.PanelsSearchRepository;
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
 * REST controller for managing Panels.
 */
@RestController
@RequestMapping("/api")
public class PanelsResource {

    private final Logger log = LoggerFactory.getLogger(PanelsResource.class);

    private static final String ENTITY_NAME = "panels";

    private final PanelsRepository panelsRepository;

    private final PanelsSearchRepository panelsSearchRepository;

    public PanelsResource(PanelsRepository panelsRepository, PanelsSearchRepository panelsSearchRepository) {
        this.panelsRepository = panelsRepository;
        this.panelsSearchRepository = panelsSearchRepository;
    }

    /**
     * POST  /panels : Create a new panels.
     *
     * @param panels the panels to create
     * @return the ResponseEntity with status 201 (Created) and with body the new panels, or with status 400 (Bad Request) if the panels has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/panels")
    @Timed
    public ResponseEntity<Panels> createPanels(@RequestBody Panels panels) throws URISyntaxException {
        log.debug("REST request to save Panels : {}", panels);
        if (panels.getId() != null) {
            throw new BadRequestAlertException("A new panels cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Panels result = panelsRepository.save(panels);
        panelsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/panels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /panels : Updates an existing panels.
     *
     * @param panels the panels to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated panels,
     * or with status 400 (Bad Request) if the panels is not valid,
     * or with status 500 (Internal Server Error) if the panels couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/panels")
    @Timed
    public ResponseEntity<Panels> updatePanels(@RequestBody Panels panels) throws URISyntaxException {
        log.debug("REST request to update Panels : {}", panels);
        if (panels.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Panels result = panelsRepository.save(panels);
        panelsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, panels.getId().toString()))
            .body(result);
    }

    /**
     * GET  /panels : get all the panels.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of panels in body
     */
    @GetMapping("/panels")
    @Timed
    public List<Panels> getAllPanels() {
        log.debug("REST request to get all Panels");
        return panelsRepository.findAll();
    }

    /**
     * GET  /panels/:id : get the "id" panels.
     *
     * @param id the id of the panels to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the panels, or with status 404 (Not Found)
     */
    @GetMapping("/panels/{id}")
    @Timed
    public ResponseEntity<Panels> getPanels(@PathVariable String id) {
        log.debug("REST request to get Panels : {}", id);
        Optional<Panels> panels = panelsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(panels);
    }

    /**
     * DELETE  /panels/:id : delete the "id" panels.
     *
     * @param id the id of the panels to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/panels/{id}")
    @Timed
    public ResponseEntity<Void> deletePanels(@PathVariable String id) {
        log.debug("REST request to delete Panels : {}", id);

        panelsRepository.deleteById(id);
        panelsSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/panels?query=:query : search for the panels corresponding
     * to the query.
     *
     * @param query the query of the panels search
     * @return the result of the search
     */
    @GetMapping("/_search/panels")
    @Timed
    public List<Panels> searchPanels(@RequestParam String query) {
        log.debug("REST request to search Panels for query {}", query);
        return StreamSupport
            .stream(panelsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
