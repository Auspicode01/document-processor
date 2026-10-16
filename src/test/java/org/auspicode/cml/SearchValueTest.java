package org.auspicode.cml;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchValueTest {

    @Test
    void whenSearchWithRegexPattern_ReturnRegexPatternFoundInFile() throws IOException {
        final String regexPattern = "\\d\\d\\d\\d-\\d\\d\\d";
        final String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        List<String> result = SearchValue.withRegex(testFile, regexPattern);

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo("2222-109");
        assertThat(result.get(1)).isEqualTo("4444-302");
    }

    @Test
    void whenSearchWithRegexPattern_ReturnEmptyList() throws IOException {
        String regexPattern = "2020-03-12T13:34:56\\.123Z";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        List<String> result = SearchValue.withRegex(testFile, regexPattern);

        assertThat(result).isEmpty();
    }
}