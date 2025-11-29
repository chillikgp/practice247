package com.ultron.acessb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ultron.acessb.enums.Category;
import com.ultron.acessb.enums.QuestionStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sdt_questions")
@Data
@Builder
public class SDTQuestion {
    @JsonProperty("_id")
    private String id;
    private String question;
    private String answer;
    private Category category;
    private QuestionStatus status;
}
