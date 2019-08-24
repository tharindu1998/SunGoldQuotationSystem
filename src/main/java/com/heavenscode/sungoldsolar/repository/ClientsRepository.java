package com.heavenscode.sungoldsolar.repository;

import com.heavenscode.sungoldsolar.domain.Clients;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Clients entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientsRepository extends MongoRepository<Clients, String> {

}
