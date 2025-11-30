package com.ultron.acessb.repository;

import com.ultron.acessb.model.SDTAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SDTAnswerRepository extends MongoRepository<SDTAnswer, String> {

    SDTAnswer findFirstByAccountIdOrderByVersionDesc(long accountId);

    List<SDTAnswer> findByAccountIdOrderByVersionDesc(long accountId);
    Optional<SDTAnswer> findByAccountIdAndVersion(long accountId, int version);

}
