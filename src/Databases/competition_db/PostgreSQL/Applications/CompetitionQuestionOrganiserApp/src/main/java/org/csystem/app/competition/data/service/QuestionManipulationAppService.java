package org.csystem.app.competition.data.service;

import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.IQuestionRepository;
import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
@Slf4j
public class QuestionManipulationAppService {
    private final IQuestionRepository m_questionRepository;

    public QuestionManipulationAppService(IQuestionRepository questionRepository)
    {
        m_questionRepository = questionRepository;
    }

    public void insertQuestion(QuestionEntity questionEntity, List<OptionEntity> options) throws SQLException
    {
        m_questionRepository.insertQuestion(questionEntity, options);
    }
}
