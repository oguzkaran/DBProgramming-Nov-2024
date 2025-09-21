package org.csystem.app.competition.data.repository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class QuestionRepository implements IQuestionRepository {
    private static final String INSERT_QUESTION = "insert into questions (description, level_id) values (?, ?)";
    private static final String CALL_SP_INSERT_OPTION = "call sp_insert_option(?, ?, ?)";
    private final JdbcTemplate m_jdbcTemplate;

    private PreparedStatement insertOptionExecuteCallback(PreparedStatement ps, OptionEntity optionEntity) throws SQLException
    {
        ps.setLong(1, optionEntity.getQuestionId());
        ps.setString(2, optionEntity.getDescription());
        ps.setBoolean(3, optionEntity.isAnswer());
        ps.execute();

        return ps;
    }

    private PreparedStatementCreator createPreparedStatementCreator(QuestionEntity questionEntity) throws SQLException
    {
        return c -> {
            var ps = c.prepareStatement(INSERT_QUESTION, new String[]{"question_id"});

            ps.setString(1, questionEntity.getDescription());
            ps.setInt(2, questionEntity.getLevelId());

            return ps;
        };
    }

    private long insertQuestion(QuestionEntity questionEntity)
    {
        try {
            var keyHolder = new GeneratedKeyHolder();

            m_jdbcTemplate.update(createPreparedStatementCreator(questionEntity), keyHolder);

            return keyHolder.getKey().longValue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertOption(OptionEntity optionEntity)
    {
        m_jdbcTemplate.execute(CALL_SP_INSERT_OPTION, (PreparedStatement ps) -> insertOptionExecuteCallback(ps, optionEntity));
    }

    @Override
    public void insertQuestion(QuestionEntity questionEntity, List<OptionEntity> options)
    {
        var questionId = insertQuestion(questionEntity);

        log.info("Question ID:{}", questionId);

        options.forEach(o -> {o.setQuestionId(questionId); insertOption(o);});
    }
}
