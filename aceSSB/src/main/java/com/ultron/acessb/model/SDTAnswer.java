package com.ultron.acessb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("sdt_answers")
@Data
public class SDTAnswer {

    @Id
    private String id;

    private String accountId;
    private int version;

    private java.util.List<QA> answers;

    private String reviewStatus;
    private String reviewerComments;

    private Instant createdTS;
    private Instant reviewUpdateTS;

    @Data
    public static class QA {
        private String questionId;
        private String response;
    }
}