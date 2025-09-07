package org.csystem.app.competition.data.repository;

import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;

public interface IQuestionRepository {
    void insertQuestion(QuestionEntity questionEntity);
    void insertOption(OptionEntity optionEntity);

    void insertQuestion(QuestionEntity questionEntity, OptionEntity optionEntity);
}
