package com.ultron.acessb.dto;

import lombok.Data;

import java.util.List;

@Data
public class BulkSSBAttemptRequest {
    private List<SSBAttemptDto> attempts;
}