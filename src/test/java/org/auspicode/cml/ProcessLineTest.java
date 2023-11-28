package org.auspicode.cml;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ProcessLineTest {

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:0", "this is why I don't like monday:5", "I want to do this, is monday ok for you?:2"}, delimiter = ':')
    void whenExtractValueAfterKeyword_ReturnKeyword(String line, int index) {
        String keyword = "this";
        String value = "monday";
        String result = ProcessLine.extractValueAfterKeyword(line, keyword, index);
        assertThat(result).isEqualTo(value);
    }

}