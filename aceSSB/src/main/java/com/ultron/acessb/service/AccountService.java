package com.ultron.acessb.service;

import com.ultron.acessb.dto.AccountUpdateRequest;
import com.ultron.acessb.enums.AccountStatus;
import com.ultron.acessb.enums.PIQReviewStatus;
import com.ultron.acessb.enums.Role;
import com.ultron.acessb.enums.SDTReviewStatus;
import com.ultron.acessb.exception.AccountNotFoundException;
import com.ultron.acessb.model.Account;
import com.ultron.acessb.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository repo;

    /**
     * Create a new account
     */
    public Account create(Account acc) {
        try {
            acc.setCreatedTS(Instant.now());
            acc.setUpdatedTS(Instant.now());

            Account saved = repo.save(acc);
            log.info("Account created successfully. accountId={}", saved.getAccountId());
            return saved;

        } catch (Exception ex) {
            log.error("Error creating account. email={}, error={}", acc.getEmail(), ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Update account details
     */
    public Account update(long accountId, AccountUpdateRequest req) {
        try {
            Account acc = repo.findByAccountId(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found for id: " + accountId));

            acc.setName(req.getName());
            acc.setPhone(req.getPhone());
            acc.setUpdatedTS(Instant.now());

            Account updated = repo.save(acc);
            log.info("Account updated successfully. accountId={}", accountId);
            return updated;

        } catch (AccountNotFoundException ex) {
            log.warn("Account update failed. Reason: {}", ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error updating account. id={}, error={}", accountId, ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Get account by accountId
     */
    public Account get(long accountId) {
        try {
            return repo.findByAccountId(accountId)
                    .orElseThrow(() -> new AccountNotFoundException("Account not found for id: " + accountId));

        } catch (AccountNotFoundException ex) {
            log.warn("Account not found. id={}", accountId);
            throw ex;

        } catch (Exception ex) {
            log.error("Unexpected error retrieving account. id={}, error={}", accountId, ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * First-time user entry (create account if email not registered)
     */
    public Account firstTimeUserEntry(String email) {
        try {
            Account existing = repo.findByEmail(email);

            if (existing != null) {
                log.info("Returning existing account for email={}", email);
                return existing;
            }

            Account newAccount = Account.builder()
                    .id(UUID.randomUUID().toString())
                    .accountId(System.currentTimeMillis() / 1000L)
                    .email(email)
                    .role(Role.user.name())
                    .piqStatus(PIQReviewStatus.not_started.name())
                    .sdtStatus(SDTReviewStatus.not_started.name())
                    .status(AccountStatus.active.name())
                    .createdTS(Instant.now())
                    .updatedTS(Instant.now())
                    .build();

            Account created = repo.save(newAccount);
            log.info("New account created for email={}", email);
            return created;

        } catch (Exception ex) {
            log.error("Error during firstTimeUserEntry. email={}, error={}", email, ex.getMessage(), ex);
            throw ex;
        }
    }
}