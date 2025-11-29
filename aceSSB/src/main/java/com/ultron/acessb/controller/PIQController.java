package com.ultron.acessb.controller;

import com.ultron.acessb.model.PIQDetails;
import com.ultron.acessb.service.PIQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/piq")
@RequiredArgsConstructor
public class PIQController {

    private final PIQService service;

    @PostMapping("/{accountId}")
    public ResponseEntity<PIQDetails> save(
            @PathVariable String accountId,
            @RequestBody PIQDetails details
    ) {
        details.setAccountId(accountId);
        return ResponseEntity.ok(service.saveNewVersion(details));
    }

    @GetMapping("/{accountId}/latest")
    public ResponseEntity<PIQDetails> getLatest(@PathVariable String accountId) {
        return ResponseEntity.ok(service.getLatest(accountId));
    }

    @GetMapping("/{accountId}/history")
    public ResponseEntity<?> getHistory(@PathVariable String accountId) {
        return ResponseEntity.ok(service.getHistory(accountId));
    }
}
