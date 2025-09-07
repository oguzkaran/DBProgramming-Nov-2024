package org.csystem.app.competition.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class BufferedReaderConfig {
    @Bean
    public BufferedReader bufferedReader(@Value("${app.data.file}") String filePath) throws IOException
    {
        return new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8));
    }
}
