package com.ultron.acessb.service;

import com.ultron.acessb.exception.AccountNotFoundException;
import com.ultron.acessb.model.SDTAnswer;
import com.ultron.acessb.model.SDTQuestion;
import com.ultron.acessb.repository.SDTAnswerRepository;
import com.ultron.acessb.repository.SDTQuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SDTService {

    private final SDTAnswerRepository answerRepo;
    private final SDTQuestionRepository questionRepo;

    /**
     * Fetch SDT questions: all or random 5
     */
    public List<SDTQuestion> getQuestions(boolean random) {
        try {
            List<SDTQuestion> questions = questionRepo.findAllActive();

            if (!random) {
                log.info("Returning all {} SDT questions", questions.size());
                return questions;
            }

            // return random 5
            Collections.shuffle(questions);
            List<SDTQuestion> randomFive = questions.stream().limit(5).toList();

            log.info("Returning random 5 out of {} SDT questions", questions.size());
            return randomFive;

        } catch (Exception ex) {
            log.error("Error fetching SDT questions: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Save a new version of SDT answers
     */
    public SDTAnswer saveNewVersion(SDTAnswer request) {
        try {
            SDTAnswer latest = answerRepo.findFirstByAccountIdOrderByVersionDesc(request.getAccountId());
            int newVersion = (latest == null) ? 1 : latest.getVersion() + 1;

            request.setVersion(newVersion);
            request.setCreatedTS(Instant.now());
            request.setReviewStatus("PENDING");

            SDTAnswer saved = answerRepo.save(request);

            log.info("Saved new SDT version {} for accountId={}", newVersion, request.getAccountId());
            return saved;

        } catch (Exception ex) {
            log.error("Error saving SDT answers for accountId={} :: {}", request.getAccountId(), ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Get latest SDT answers
     */
    public SDTAnswer getLatest(String accountId) {
        try {
            SDTAnswer latest = answerRepo.findFirstByAccountIdOrderByVersionDesc(accountId);

            if (latest == null) {
                log.warn("No SDT answers found for accountId={}", accountId);
                throw new AccountNotFoundException("No SDT answers found for accountId: " + accountId);
            }

            return latest;

        } catch (AccountNotFoundException ex) {
            log.warn(ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            log.error("Error retrieving latest SDT for accountId={} :: {}", accountId, ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Get history of all SDT answers (sorted desc)
     */
    public List<SDTAnswer> getHistory(String accountId) {
        try {
            List<SDTAnswer> history = answerRepo.findByAccountIdOrderByVersionDesc(accountId);

            if (history.isEmpty()) {
                log.warn("No SDT answer history found for accountId={}", accountId);
                throw new AccountNotFoundException("No SDT history found for accountId: " + accountId);
            }

            return history;

        } catch (AccountNotFoundException ex) {
            log.warn(ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            log.error("Error retrieving SDT history for accountId={} :: {}", accountId, ex.getMessage(), ex);
            throw ex;
        }
    }

    public SDTQuestion insertQuestion(SDTQuestion question) {
        try {
            question.setStatus("ACTIVE");
            SDTQuestion saved = questionRepo.save(question);

            log.info("SDT question inserted successfully. id={}", saved.getId());
            return saved;

        } catch (Exception ex) {
            log.error("Error inserting SDT question. text={}, error={}", question.getText(), ex.getMessage(), ex);
            throw ex;
        }
    }
}