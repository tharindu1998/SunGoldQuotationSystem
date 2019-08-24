package com.heavenscode.sungoldsolar.repository;

import com.heavenscode.sungoldsolar.domain.Quotation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Quotation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuotationRepository extends MongoRepository<Quotation, String> {

    List<Quotation> findByAddressId(String addressId);

}
