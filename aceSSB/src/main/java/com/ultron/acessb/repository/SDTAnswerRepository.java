package com.ultron.acessb.repository;

import com.ultron.acessb.model.SDTAnswer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SDTAnswerRepository extends MongoRepository<SDTAnswer, String> {

    SDTAnswer findFirstByAccountIdOrderByVersionDesc(String accountId);

    List<SDTAnswer> findByAccountIdOrderByVersionDesc(String accountId);
}
