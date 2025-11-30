package com.ultron.acessb.controller;

import com.ultron.acessb.dto.SDTQuestionDto;
import com.ultron.acessb.model.SDTQuestion;
import com.ultron.acessb.service.SDTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<SDTQuestion>> addQuestion(@RequestBody List<SDTQuestionDto> question) {
        log.info("Received request to insert SDT question: {}", question.size());
        return ResponseEntity.ok(service.insertQuestion(question));
    }
}
