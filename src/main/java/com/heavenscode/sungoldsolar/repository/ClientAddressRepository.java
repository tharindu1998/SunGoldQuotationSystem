package com.heavenscode.sungoldsolar.repository;

import java.util.Optional;

import com.heavenscode.sungoldsolar.domain.ClientAddress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the ClientAddress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientAddressRepository extends MongoRepository<ClientAddress, String> {
    Optional<ClientAddress> findByClientId(String client);
}
