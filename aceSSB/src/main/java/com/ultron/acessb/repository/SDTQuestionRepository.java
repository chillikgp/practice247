package com.ultron.acessb.repository;

import com.ultron.acessb.model.SDTQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface SDTQuestionRepository extends MongoRepository<SDTQuestion, String> {

    @Query("{ 'status': 'active' }")
    List<SDTQuestion> findAllActive();
}
