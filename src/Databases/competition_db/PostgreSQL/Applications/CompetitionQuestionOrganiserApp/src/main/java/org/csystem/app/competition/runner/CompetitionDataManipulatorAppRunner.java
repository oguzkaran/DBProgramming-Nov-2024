package org.csystem.app.competition.runner;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.csystem.app.competition.data.repository.entity.OptionEntity;
import org.csystem.app.competition.data.repository.entity.QuestionEntity;
import org.csystem.app.competition.data.service.QuestionManipulationAppService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
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
            var options = Arrays.stream(lineInfo).skip(2).limit(lineInfo.length - 2 - 1)
                    .map(o -> OptionEntity.builder().description(o).build()).toList();

            options.get(lineInfo[lineInfo.length - 1].charAt(0) - 'A').setAnswer(true);
            m_questionManipulationAppService.insertQuestion(questionEntity, options);
        }
        catch (SQLException ex) {
            log.error("SQL Exception occurred:{}", ex.getMessage());
        }
        catch (Exception ex) {
            log.error("Exception occurred in lineCallback:{}", ex.getMessage());
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
            log.error("Exception occurred loadAndManipulateDataCallback:{}", ex.getMessage());
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        m_executorService.execute(this::loadAndManipulateDataCallback);
    }
}
