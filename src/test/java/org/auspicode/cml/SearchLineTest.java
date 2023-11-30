package org.auspicode.cml;

import org.auspicode.cml.exception.KeywordNotFoundException;
import org.auspicode.cml.exception.LineOutOfReachException;
import org.auspicode.cml.exception.OffsetIsZeroException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.auspicode.cml.exception.ErrorMessages.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchLineTest {

    @Test
    void whenSearchWithKeyword_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withKeyword(testFile, keyword);

        assertThat(result).contains(keyword);
    }

    @Test
    void whenSearchWithKeywordNotInFile_ReturnKeywordNotFoundException() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        KeywordNotFoundException keywordNotFoundException = assertThrows(KeywordNotFoundException.class, () -> SearchLine.withKeyword(testFile, keyword));

        assertThat(keywordNotFoundException.getMessage()).isEqualTo(KEYWORD_NOT_FOUND);
    }

    @Test
    void whenSearchWithKeywordWithPositiveOffset_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withOffset(testFile, keyword, 3);

        assertThat(result).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchWithKeywordWithNegativeOffset_ReturnKeyword() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withOffset(testFile, keyword, -3);

        assertThat(result).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchWithKeywordWithOffsetSetToZero_ReturnOffsetIsZeroException() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        OffsetIsZeroException offsetIsZeroException = assertThrows(OffsetIsZeroException.class, () -> SearchLine.withOffset(testFile, keyword, 0));

        assertThat(offsetIsZeroException.getMessage()).isEqualTo(OFFSET_IS_ZERO);
    }

    @Test
    void whenSearchWithKeywordNotInFileWithOffset_ReturnKeywordNotFoundException() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        KeywordNotFoundException keywordNotFoundException = assertThrows(KeywordNotFoundException.class, () -> SearchLine.withOffset(testFile, keyword, 1));

        assertThat(keywordNotFoundException.getMessage()).isEqualTo(KEYWORD_NOT_FOUND);
    }

    @Test
    void whenSearchWithKeywordWithOffset_ReturnLineOutOfReachException() throws IOException {
        String keyword = "Tenth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        LineOutOfReachException lineOutOfReachException = assertThrows(LineOutOfReachException.class, () -> SearchLine.withOffset(testFile, keyword, 1));

        assertThat(lineOutOfReachException.getMessage()).isEqualTo(LINE_OUT_OF_REACH);
    }
}