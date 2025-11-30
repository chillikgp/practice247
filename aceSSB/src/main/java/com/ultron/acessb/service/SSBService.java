package com.ultron.acessb.service;

import com.ultron.acessb.dto.BulkSSBAttemptRequest;
import com.ultron.acessb.dto.SSBAttemptDto;
import com.ultron.acessb.model.SSBAttempts;
import com.ultron.acessb.repository.SSBAttemptsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SSBService {

    private final SSBAttemptsRepository repo;

    /**
     * Bulk upsert SSB attempts with version support.
     */
    public List<SSBAttempts> saveAttempts(BulkSSBAttemptRequest request) {
        List<SSBAttempts> result = new ArrayList<>();

        for (SSBAttemptDto dto : request.getAttempts()) {
            result.add(upsertAttempt(dto));
        }

        log.info("Saved {} SSB attempts for account={}", result.size(),
                request.getAttempts().get(0).getAccountId());

        return result;
    }

    /**
     * Insert or Update based on existing attempt.
     */
    public SSBAttempts upsertAttempt(SSBAttemptDto dto) {
        try {
            var existingOpt = repo.findFirstByAccountIdAndBoardAndEntryAndAttemptDateOrderByVersionDesc(
                    dto.getAccountId(),
                    dto.getBoard(),
                    dto.getEntry(),
                    dto.getAttemptDate()
            );

            int newVersion;
            SSBAttempts newEntry;

            if (existingOpt.isPresent()) {
                var existing = existingOpt.get();
                newVersion = existing.getVersion() + 1;

                newEntry = SSBAttempts.builder()
                        .id(UUID.randomUUID().toString())
                        .accountId(dto.getAccountId())
                        .board(dto.getBoard())
                        .entry(dto.getEntry())
                        .attemptDate(dto.getAttemptDate())
                        .version(newVersion)
                        .createdTS(existing.getCreatedTS())   // preserve original createdTS
                        .updateTS(Instant.now())
                        .build();

                log.info("Updating existing attempt with new version={} for accountId={}",
                        newVersion, dto.getAccountId());

            } else {
                newVersion = 1;
                newEntry = SSBAttempts.builder()
                        .id(UUID.randomUUID().toString())
                        .accountId(dto.getAccountId())
                        .board(dto.getBoard())
                        .entry(dto.getEntry())
                        .attemptDate(dto.getAttemptDate())
                        .version(newVersion)
                        .createdTS(Instant.now())
                        .updateTS(Instant.now())
                        .build();
                log.info("Inserting new SSB attempt: version=1 for accountId={}", dto.getAccountId());
            }
            return repo.save(newEntry);

        } catch (Exception ex) {
            log.error("Error saving attempt for accountId={} : {}", dto.getAccountId(), ex.getMessage(), ex);
            throw ex;
        }
    }

    public List<SSBAttempts> getAttempts(long accountId) {
        return repo.findByAccountIdOrderByCreatedTSDesc(accountId);
    }

    public void deleteAttempt(String id) {
        repo.deleteById(id);
        log.info("Deleted SSB attempt with id={}", id);
    }
}