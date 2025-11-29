package com.ultron.acessb.repository;

import com.ultron.acessb.model.PIQDetails;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PIQRepository extends MongoRepository<PIQDetails, String> {

    List<PIQDetails> findByAccountIdOrderByVersionDesc(String accountId);

    PIQDetails findFirstByAccountIdOrderByVersionDesc(String accountId);
}
