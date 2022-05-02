package br.com.unb.comptest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.commons.io.IOUtils;


@CompTest
public class BaseTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    protected   String format(Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        object.toString().lines().forEach(line -> {
            if (!line.isEmpty() && !line.isBlank()) {
                stringBuilder.append(line.trim());
            }
        });

        return stringBuilder.toString()
                .replaceAll(" ", "")
                .replaceAll(",", ",\n")
                .replaceAll(";", ",\n")
                .trim();
    }

    protected <T> T getResourceAsClass(String path, Class<T> clazz) {
        final String result = getResourceAsString(path);

        try {
            return objectMapper.readValue(result, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    protected String getResourceAsString(String path) {
        try {
            return new String(Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
