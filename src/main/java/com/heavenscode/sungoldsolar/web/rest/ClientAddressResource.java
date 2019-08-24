package com.heavenscode.sungoldsolar.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.heavenscode.sungoldsolar.domain.ClientAddress;
import com.heavenscode.sungoldsolar.repository.ClientAddressRepository;
import com.heavenscode.sungoldsolar.repository.search.ClientAddressSearchRepository;
import com.heavenscode.sungoldsolar.web.rest.errors.BadRequestAlertException;
import com.heavenscode.sungoldsolar.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ClientAddress.
 */
@RestController
@RequestMapping("/api")
public class ClientAddressResource {

    private final Logger log = LoggerFactory.getLogger(ClientAddressResource.class);

    private static final String ENTITY_NAME = "clientAddress";

    private final ClientAddressRepository clientAddressRepository;

    private final ClientAddressSearchRepository clientAddressSearchRepository;

    public ClientAddressResource(ClientAddressRepository clientAddressRepository,
            ClientAddressSearchRepository clientAddressSearchRepository) {
        this.clientAddressRepository = clientAddressRepository;
        this.clientAddressSearchRepository = clientAddressSearchRepository;
    }

    /**
     * POST /client-addresses : Create a new clientAddress.
     *
     * @param clientAddress the clientAddress to create
     * @return the ResponseEntity with status 201 (Created) and with body the new
     *         clientAddress, or with status 400 (Bad Request) if the clientAddress
     *         has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/client-addresses")
    @Timed
    public ResponseEntity<ClientAddress> createClientAddress(@RequestBody ClientAddress clientAddress)
            throws URISyntaxException {
        log.debug("REST request to save ClientAddress : {}", clientAddress);
        if (clientAddress.getId() != null) {
            throw new BadRequestAlertException("A new clientAddress cannot already have an ID", ENTITY_NAME,
                    "idexists");
        }
        ClientAddress result = clientAddressRepository.save(clientAddress);
        clientAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/client-addresses/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
    }

    /**
     * PUT /client-addresses : Updates an existing clientAddress.
     *
     * @param clientAddress the clientAddress to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     *         clientAddress, or with status 400 (Bad Request) if the clientAddress
     *         is not valid, or with status 500 (Internal Server Error) if the
     *         clientAddress couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/client-addresses")
    @Timed
    public ResponseEntity<ClientAddress> updateClientAddress(@RequestBody ClientAddress clientAddress)
            throws URISyntaxException {
        log.debug("REST request to update ClientAddress : {}", clientAddress);
        if (clientAddress.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClientAddress result = clientAddressRepository.save(clientAddress);
        clientAddressSearchRepository.save(result);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, clientAddress.getId().toString()))
                .body(result);
    }

    /**
     * GET /client-addresses : get all the clientAddresses.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of
     *         clientAddresses in body
     */
    @GetMapping("/client-addresses")
    @Timed
    public List<ClientAddress> getAllClientAddresses() {
        log.debug("REST request to get all ClientAddresses");
        return clientAddressRepository.findAll();
    }

    /**
     * GET /client-addresses/:id : get the "id" clientAddress.
     *
     * @param id the id of the clientAddress to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     *         clientAddress, or with status 404 (Not Found)
     */
    @GetMapping("/client-addresses/{id}")
    @Timed
    public ResponseEntity<ClientAddress> getClientAddress(@PathVariable String id) {
        log.debug("REST request to get ClientAddress : {}", id);
        Optional<ClientAddress> clientAddress = clientAddressRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(clientAddress);
    }

    /**
     * DELETE /client-addresses/:id : delete the "id" clientAddress.
     *
     * @param id the id of the clientAddress to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/client-addresses/{id}")
    @Timed
    public ResponseEntity<Void> deleteClientAddress(@PathVariable String id) {
        log.debug("REST request to delete ClientAddress : {}", id);

        clientAddressRepository.deleteById(id);
        clientAddressSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }

    /**
     * SEARCH /_search/client-addresses?query=:query : search for the clientAddress
     * corresponding to the query.
     *
     * @param query the query of the clientAddress search
     * @return the result of the search
     */
    @GetMapping("/_search/client-addresses")
    @Timed
    public List<ClientAddress> searchClientAddresses(@RequestParam String query) {
        log.debug("REST request to search ClientAddresses for query {}", query);

        return StreamSupport.stream(clientAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("/client-addresses/_search/{clientId}")
    @Timed
    public List<ClientAddress> getClientAddressByClientId(@PathVariable String clientId) {
        log.debug("REST request to get ClientAddress : {}", clientId); 
        return clientAddressSearchRepository.findByClientId(clientId);

    }

}
