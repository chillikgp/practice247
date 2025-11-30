package com.ultron.acessb.repository;

import com.ultron.acessb.enums.Board;
import com.ultron.acessb.enums.EntryType;
import com.ultron.acessb.model.SSBAttempts;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SSBAttemptsRepository extends MongoRepository<SSBAttempts, String> {

    List<SSBAttempts> findByAccountIdOrderByCreatedTSDesc(long accountId);

    Optional<SSBAttempts> findFirstByAccountIdAndBoardAndEntryAndAttemptDateOrderByVersionDesc(
            long accountId,
            Board board,
            EntryType entry,
            String attemptDate
    );
}