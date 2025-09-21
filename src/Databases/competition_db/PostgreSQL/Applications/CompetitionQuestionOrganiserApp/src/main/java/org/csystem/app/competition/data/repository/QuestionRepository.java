package org.csystem.app.competition.data.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class QuestionRepository implements IQuestionRepository {
    private static final String CALL_SP_INSERT_QUESTION = "call sp_insert_question(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final JdbcTemplate m_jdbcTemplate;

    private PreparedStatement insertQuestionExecuteCallback(PreparedStatement ps, QuestionEntity questionEntity, OptionEntity...optionEntity) throws SQLException
    {
        ps.setString(1, questionEntity.getDescription());
        ps.setInt(2, questionEntity.getLevelId());
        ps.setString(3, optionEntity[0].getDescription());
        ps.setBoolean(4, optionEntity[0].isAnswer());
        ps.setString(5, optionEntity[1].getDescription());
        ps.setBoolean(6, optionEntity[1].isAnswer());
        ps.setString(7, optionEntity[2].getDescription());
        ps.setBoolean(8, optionEntity[2].isAnswer());
        ps.setString(9, optionEntity[3].getDescription());
        ps.setBoolean(10, optionEntity[3].isAnswer());
        ps.execute();

        return ps;
    }

    private void insertQuestion(QuestionEntity questionEntity, OptionEntity...optionEntity)
    {
        m_jdbcTemplate.execute(CALL_SP_INSERT_QUESTION, (PreparedStatement ps) -> insertQuestionExecuteCallback(ps, questionEntity, optionEntity));
    }


    @Override
    public void insertQuestion(QuestionEntity questionEntity, List<OptionEntity> options)
    {
        insertQuestion(questionEntity, options.get(0), options.get(1),  options.get(2), options.get(3));
    }
}
