package com.ultron.acessb.controller;

import com.ultron.acessb.model.SDTAnswer;
import com.ultron.acessb.model.SDTQuestion;
import com.ultron.acessb.service.SDTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sdt")
@RequiredArgsConstructor
public class SDTController {

    private final SDTService service;

    /**
     * Fetch SDT Questions
     * /sdt/questions            -> fetch ALL
     * /sdt/questions?random=true -> fetch random 5
     */
    @GetMapping("/questions")
    public ResponseEntity<List<SDTQuestion>> getQuestions(
            @RequestParam(defaultValue = "false") boolean random
    ) {
        log.info("Fetching SDT questions. random={}", random);
        List<SDTQuestion> questions = service.getQuestions(random);
        return ResponseEntity.ok(questions);
    }

    /**
     * Submit SDT Answers (creates version)
     */
    @PostMapping("/answer/{accountId}")
    public ResponseEntity<SDTAnswer> submitAnswers(
            @PathVariable String accountId,
            @RequestBody SDTAnswer request
    ) {
        log.info("Received SDT answers for accountId={}", accountId);
        request.setAccountId(accountId);
        SDTAnswer saved = service.saveNewVersion(request);
        return ResponseEntity.ok(saved);
    }

    /**
     * Get the latest SDT Answer version
     */
    @GetMapping("/answer/{accountId}/latest")
    public ResponseEntity<SDTAnswer> getLatest(@PathVariable String accountId) {
        log.info("Fetching latest SDT answers: accountId={}", accountId);
        return ResponseEntity.ok(service.getLatest(accountId));
    }

    /**
     * Get SDT Answer history (all versions sorted desc)
     */
    @GetMapping("/answer/{accountId}/history")
    public ResponseEntity<List<SDTAnswer>> getHistory(@PathVariable String accountId) {
        log.info("Fetching SDT answer history: accountId={}", accountId);
        return ResponseEntity.ok(service.getHistory(accountId));
    }
}
