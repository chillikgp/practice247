package com.ultron.acessb.dto;

import com.ultron.acessb.model.SDTAnswer;
import lombok.Data;

@Data
public class SDTAnswerDto {
    private long accountId;
    private java.util.List<SDTAnswer.QA> answers;

}
