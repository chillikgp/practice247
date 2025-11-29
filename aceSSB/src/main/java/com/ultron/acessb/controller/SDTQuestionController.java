package com.ultron.acessb.controller;

import com.ultron.acessb.model.SDTQuestion;
import com.ultron.acessb.service.SDTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/sdt/question")
@RequiredArgsConstructor
public class SDTQuestionController {

    private final SDTService service;

    /**
     * Create / Insert new SDT question
     */
    @PostMapping
    public ResponseEntity<SDTQuestion> addQuestion(@RequestBody SDTQuestion question) {
        log.info("Received request to insert SDT question: {}", question.getText());
        SDTQuestion created = service.insertQuestion(question);
        return ResponseEntity.ok(created);
    }
}
