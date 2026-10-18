package org.auspicode.cml;

import org.auspicode.cml.exception.OffsetIsZeroException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.auspicode.cml.exception.ErrorMessages.OFFSET_IS_ZERO;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchLineTest {

    @Test
    void whenSearchWithKeyword_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withKeyword(testFile, keyword);

        assertTrue(result.isPresent());
        assertThat(result.get()).contains(keyword);
    }

    @Test
    void whenSearchWithKeywordNotInFile_ReturnEmptyString() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withKeyword(testFile, keyword);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenSearchWithKeywordInEmptyFile_ReturnEmptyString() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/emptyTestFile"));
        Optional<String> result = SearchLine.withKeyword(testFile, keyword);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenSearchWithKeywordWithPositiveOffset_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, 3);

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchFirstLineWithKeywordWithPositiveOffset_ReturnKeyword() throws IOException {
        String keyword = "First";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, 3);

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo("Fourth Line");
    }

    @Test
    void whenSearchWithKeywordWithNegativeOffset_ReturnKeyword() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, -3);

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchLastLineWithKeywordWithNegativeOffset_ReturnKeyword() throws IOException {
        String keyword = "Tenth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, -9);

        assertTrue(result.isPresent());
        assertThat(result.get()).isEqualTo("First Line");
    }

    @Test
    void whenSearchWithKeywordWithOffsetSetToZero_ReturnOffsetIsZeroException() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        OffsetIsZeroException offsetIsZeroException = assertThrows(OffsetIsZeroException.class, () -> SearchLine.withOffset(testFile, keyword, 0));

        assertThat(offsetIsZeroException.getMessage()).isEqualTo(OFFSET_IS_ZERO);
    }

    @Test
    void whenSearchWithKeywordNotInFileWithOffset_ReturnEmptyString() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, 1);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenSearchWithKeywordWithOffsetOutOfBounds_ReturnEmptyString() throws IOException {
        String keyword = "Tenth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, 1);

        assertTrue(result.isEmpty());
    }

    @Test
    void whenSearchWithKeywordWithOffsetInEmptyFile_ReturnEmptyString() throws IOException {
        String keyword = "Tenth";
        String testFile = Files.readString(Path.of("src/test/resources/emptyTestFile"));
        Optional<String> result = SearchLine.withOffset(testFile, keyword, 1);

        assertTrue(result.isEmpty());
    }
}