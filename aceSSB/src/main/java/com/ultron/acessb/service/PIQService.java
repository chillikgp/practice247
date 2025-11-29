package com.ultron.acessb.service;

import com.ultron.acessb.model.PIQDetails;
import com.ultron.acessb.repository.PIQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PIQService {

    private final PIQRepository repo;

    public PIQDetails saveNewVersion(PIQDetails details) {

        PIQDetails latest = repo.findFirstByAccountIdOrderByVersionDesc(details.getAccountId());
        int newVersion = latest == null ? 1 : latest.getVersion() + 1;

        details.setVersion(newVersion);
        details.setCreatedTS(Instant.now());
        return repo.save(details);
    }

    public PIQDetails getLatest(String accountId) {
        return repo.findFirstByAccountIdOrderByVersionDesc(accountId);
    }

    public java.util.List<PIQDetails> getHistory(String accountId) {
        return repo.findByAccountIdOrderByVersionDesc(accountId);
    }
}
