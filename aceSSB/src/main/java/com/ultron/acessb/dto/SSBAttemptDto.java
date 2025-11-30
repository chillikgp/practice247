package com.ultron.acessb.dto;

import com.ultron.acessb.enums.Board;
import com.ultron.acessb.enums.EntryType;
import lombok.Data;

@Data
public class SSBAttemptDto {
    private long accountId;
    private Board board;
    private EntryType entry;
    private String attemptDate; // YYYY-MM-DD or similar
}