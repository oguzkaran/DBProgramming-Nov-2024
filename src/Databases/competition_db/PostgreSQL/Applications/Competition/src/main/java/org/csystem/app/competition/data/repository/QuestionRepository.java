package org.csystem.app.competition.data.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
@Slf4j
@AllArgsConstructor
public class QuestionRepository implements IQuestionRepository {
    private static final String INSERT_QUESTION = "insert into questions (description, level_id) values (?, ?)";
    private static final String INSERT_OPTION = "insert into options values (?, ?, ?)";
    private final JdbcTemplate m_jdbcTemplate;

    private PreparedStatement insertQuestionExecuteCallback(PreparedStatement ps, QuestionEntity questionEntity) throws SQLException
    {
        ps.setString(1, questionEntity.getDescription());
        ps.setInt(2, questionEntity.getLevelId());

        ps.execute();
        return ps;
    }

    @Override
    public void insertQuestion(QuestionEntity questionEntity)
    {
        m_jdbcTemplate.execute(INSERT_QUESTION, (PreparedStatement ps) -> insertQuestionExecuteCallback(ps, questionEntity));
    }

    @Override
    public void insertOption(OptionEntity optionEntity)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void insertQuestion(QuestionEntity questionEntity, OptionEntity optionEntity)
    {
        //Will be modified to be transaction safe
        insertQuestion(questionEntity);
        insertOption(optionEntity);
    }
}
