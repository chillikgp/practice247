package com.ultron.acessb.model;

import com.ultron.acessb.enums.Board;
import com.ultron.acessb.enums.EntryType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("ssb_attempts")
@Builder
public class SSBAttempts {

    @Id
    private String id;

    private long accountId;
    private int version;
    private Board board;
    private EntryType entry;
    private String attemptDate;
    private Instant createdTS;
    private Instant updateTS;
}
