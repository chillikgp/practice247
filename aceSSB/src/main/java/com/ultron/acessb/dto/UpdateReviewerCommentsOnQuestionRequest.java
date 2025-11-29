package com.ultron.acessb.dto;

import com.ultron.acessb.model.SDTAnswer;
import lombok.Data;

import java.util.List;

@Data
public class UpdateReviewerCommentsOnQuestionRequest {
    private List<SDTAnswer.QA> reviewerCommentsOnQuestion;
}