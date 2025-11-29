package com.ultron.acessb.repository;


import com.ultron.acessb.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {
    Optional<Account> findByAccountId(long accountId);

    Account findByEmail(String email);
}
