package com.ultron.acessb.controller;

import com.ultron.acessb.dto.AccountUpdateRequest;
import com.ultron.acessb.model.Account;
import com.ultron.acessb.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService service;

    /**
     * First-time user entry - creates a user if email not found
     */
    @PostMapping("/first-time")
    public ResponseEntity<Account> firstTimeUserEntry(@RequestParam String email) {
        log.info("Received first-time user entry request: email={}", email);
        Account account = service.firstTimeUserEntry(email);
        return ResponseEntity.ok(account);
    }

    /**
     * Update account details
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> update(
            @PathVariable("id") long accountId,
            @RequestBody AccountUpdateRequest req
    ) {
        log.info("Received account update request: id={}", accountId);
        Account updated = service.update(accountId, req);
        return ResponseEntity.ok(updated);
    }

    /**
     * Fetch account by accountId
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> get(@PathVariable("id") long accountId) {
        log.info("Received fetch account request: id={}", accountId);
        Account acc = service.get(accountId);
        return ResponseEntity.ok(acc);
    }
}