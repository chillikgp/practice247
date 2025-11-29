package com.ultron.acessb.dto;

import com.ultron.acessb.enums.Category;
import com.ultron.acessb.enums.QuestionStatus;
import lombok.Data;

@Data
public class SDTQuestionDto {
    private final String question;
    private final Category category;
    private final String answer;
    private final QuestionStatus status;
}
