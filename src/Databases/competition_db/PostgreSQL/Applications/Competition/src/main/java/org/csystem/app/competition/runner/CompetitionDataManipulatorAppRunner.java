package org.csystem.app.competition.runner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.csystem.app.competition.data.service.QuestionManipulationAppService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

@Component
@AllArgsConstructor
@Slf4j
public class CompetitionDataManipulatorAppRunner implements ApplicationRunner {
    private final ExecutorService m_executorService;
    private final BufferedReader m_bufferedReader;
    private final QuestionManipulationAppService m_questionManipulationAppService;

    private void lineCallback(String line)
    {
        try {
            log.info(line);

            var lineInfo = line.split("[@]");

            var questionEntity = QuestionEntity.builder().levelId(Integer.parseInt(lineInfo[0])).description(lineInfo[1]).build();
            m_questionManipulationAppService.insertQuestion(questionEntity);
        }
        catch (SQLException ex) {
            log.error("SQL Exception occurred:{}", ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Exception occurred:{}", ex.getMessage());
        }
    }

    private void loadAndManipulateDataCallback()
    {
        try (m_bufferedReader) {
            m_bufferedReader.lines().skip(1).forEach(this::lineCallback);
        }
        catch (IOException ex) {
            log.error("IO Exception occurred:{}", ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Exception occurred:{}", ex.getMessage());
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        m_executorService.execute(this::loadAndManipulateDataCallback);
    }
}
