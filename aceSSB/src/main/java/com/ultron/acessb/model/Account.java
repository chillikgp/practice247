package com.ultron.acessb.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("accounts")
public class Account {

    @JsonProperty("_id")
    private String id;

    private long accountId;
    private String name;
    private String email;
    private String phone;

    private String role;
    private String status;

    private String piqStatus;
    private String sdtStatus;

    private Instant createdTS;
    private Instant updatedTS;
}