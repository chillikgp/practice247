package com.ultron.acessb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("sdt_answers")
@Data
@Builder
public class SDTAnswer {

    @Id
    private String id;

    private long accountId;
    private int version;

    private java.util.List<QA> answers;

    private String reviewStatus;
    private String reviewerComments;
    private String aiReviewComments;
    private java.util.List<QA> reviewerCommentsOnQuestion;


    private Instant createdTS;
    private Instant reviewUpdateTS;

    @Data
    public static class QA {
        private String questionId;
        private String response;
    }
}