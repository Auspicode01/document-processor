package org.auspicode.cml;

import org.auspicode.cml.exception.KeywordNotFoundException;
import org.auspicode.cml.exception.OffsetIsZeroException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.auspicode.cml.exception.ErrorMessages.KEYWORD_NOT_FOUND;
import static org.auspicode.cml.exception.ErrorMessages.OFFSET_IS_ZERO;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SearchLineTest {

    @Test
    void whenSearchByKeyword_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withKeyword(testFile, keyword);

        assertThat(result).contains(keyword);
    }

    @Test
    void whenSearchByKeywordNotInFile_ReturnKeywordNotFoundException() throws IOException {
        String keyword = "Eleventh";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        KeywordNotFoundException keywordNotFoundException = assertThrows(KeywordNotFoundException.class, () -> SearchLine.withKeyword(testFile, keyword));

        assertThat(keywordNotFoundException.getMessage()).isEqualTo(KEYWORD_NOT_FOUND);
    }

    @Test
    void whenSearchByKeywordWithPositiveOffset_ReturnKeyword() throws IOException {
        String keyword = "Third";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withOffset(testFile, keyword, 3);

        assertThat(result).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchByKeywordWithNegativeOffset_ReturnKeyword() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        String result = SearchLine.withOffset(testFile, keyword, -3);

        assertThat(result).isEqualTo("Sixth Line");
    }

    @Test
    void whenSearchByKeywordWithOffsetSetToZero_ReturnKeywordNotFoundException() throws IOException {
        String keyword = "Ninth";
        String testFile = Files.readString(Path.of("src/test/resources/testFile"));
        OffsetIsZeroException offsetIsZeroException = assertThrows(OffsetIsZeroException.class, () -> SearchLine.withOffset(testFile, keyword, 0));

        assertThat(offsetIsZeroException.getMessage()).isEqualTo(OFFSET_IS_ZERO);
    }
}