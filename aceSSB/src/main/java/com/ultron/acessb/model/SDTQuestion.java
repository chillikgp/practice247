package com.ultron.acessb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("sdt_questions")
@Data
public class SDTQuestion {
    @JsonProperty("_id")
    private String id;
    private String text;
    private String category;
    private String status;
}
