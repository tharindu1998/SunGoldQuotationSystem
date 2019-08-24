package com.heavenscode.sungoldsolar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.heavenscode.sungoldsolar.domain.Clients;
import com.heavenscode.sungoldsolar.repository.ClientsRepository;
import com.heavenscode.sungoldsolar.repository.search.ClientsSearchRepository;
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
 * REST controller for managing Clients.
 */
@RestController
@RequestMapping("/api")
public class ClientsResource {

    private final Logger log = LoggerFactory.getLogger(ClientsResource.class);

    private static final String ENTITY_NAME = "clients";

    private final ClientsRepository clientsRepository;

    private final ClientsSearchRepository clientsSearchRepository;

    public ClientsResource(ClientsRepository clientsRepository, ClientsSearchRepository clientsSearchRepository) {
        this.clientsRepository = clientsRepository;
        this.clientsSearchRepository = clientsSearchRepository;
    }

    /**
     * POST  /clients : Create a new clients.
     *
     * @param clients the clients to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clients, or with status 400 (Bad Request) if the clients has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clients")
    @Timed
    public ResponseEntity<Clients> createClients(@RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to save Clients : {}", clients);
        if (clients.getId() != null) {
            throw new BadRequestAlertException("A new clients cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clients result = clientsRepository.save(clients);
        clientsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clients : Updates an existing clients.
     *
     * @param clients the clients to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clients,
     * or with status 400 (Bad Request) if the clients is not valid,
     * or with status 500 (Internal Server Error) if the clients couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clients")
    @Timed
    public ResponseEntity<Clients> updateClients(@RequestBody Clients clients) throws URISyntaxException {
        log.debug("REST request to update Clients : {}", clients);
        if (clients.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clients result = clientsRepository.save(clients);
        clientsSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clients.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clients : get all the clients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of clients in body
     */
    @GetMapping("/clients")
    @Timed
    public List<Clients> getAllClients() {
        log.debug("REST request to get all Clients");
        return clientsRepository.findAll();
    }

    /**
     * GET  /clients/:id : get the "id" clients.
     *
     * @param id the id of the clients to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clients, or with status 404 (Not Found)
     */
    @GetMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Clients> getClients(@PathVariable String id) {
        log.debug("REST request to get Clients : {}", id);
        Optional<Clients> clients = clientsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clients);
    }

    /**
     * DELETE  /clients/:id : delete the "id" clients.
     *
     * @param id the id of the clients to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteClients(@PathVariable String id) {
        log.debug("REST request to delete Clients : {}", id);

        clientsRepository.deleteById(id);
        clientsSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH  /_search/clients?query=:query : search for the clients corresponding
     * to the query.
     *
     * @param query the query of the clients search
     * @return the result of the search
     */
    @GetMapping("/_search/clients")
    @Timed
    public List<Clients> searchClients(@RequestParam String query) {
        log.debug("REST request to search Clients for query {}", query);
        return StreamSupport
            .stream(clientsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
