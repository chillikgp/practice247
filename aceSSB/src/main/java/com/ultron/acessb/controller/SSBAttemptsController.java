package com.ultron.acessb.controller;

import com.ultron.acessb.dto.BulkSSBAttemptRequest;
import com.ultron.acessb.model.SSBAttempts;
import com.ultron.acessb.service.SSBService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/ssb")
@RequiredArgsConstructor
public class SSBAttemptsController {

    private final SSBService service;

    /**
     * Bulk upsert SSB attempts
     */
    @PostMapping("/attempts")
    public ResponseEntity<List<SSBAttempts>> saveAttempts(
            @RequestBody BulkSSBAttemptRequest request) {
        log.info("Received SSB attempts upsert request. count={}", request.getAttempts().size());
        return ResponseEntity.ok(service.saveAttempts(request));
    }

    /**
     * Fetch all SSB attempts
     */
    @GetMapping("/attempts/{accountId}")
    public ResponseEntity<List<SSBAttempts>> getAttempts(
            @PathVariable long accountId) {
        return ResponseEntity.ok(service.getAttempts(accountId));
    }

    /**
     * Delete one attempt
     */
    @DeleteMapping("/attempt/{id}")
    public ResponseEntity<Void> deleteAttempt(@PathVariable String id) {
        service.deleteAttempt(id);
        return ResponseEntity.noContent().build();
    }
}