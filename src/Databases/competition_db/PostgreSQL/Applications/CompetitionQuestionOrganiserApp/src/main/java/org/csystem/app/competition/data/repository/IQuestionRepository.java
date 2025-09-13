package org.csystem.app.competition.data.repository;

import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;

import java.util.List;

public interface IQuestionRepository {
    void insertQuestion(QuestionEntity questionEntity, List<OptionEntity> options);
}