package org.auspicode.cml;

import org.auspicode.cml.exception.WordOutOfReachException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.auspicode.cml.exception.ErrorMessages.WORD_OUT_OF_REACH;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProcessLineTest {

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:0", "this is why I don't like monday:5", "I want to do this, is monday ok for you?:2"}, delimiter = ':')
    void whenExtractValueAfterKeyword_ReturnValue(String line, int index) {
        String keyword = "this";
        String value = "monday";
        String result = ProcessLine.extractValueAfterKeyword(line, keyword, index);
        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:monday:is a beautiful day", "this is why I don't like monday:why:I don't like monday", "I want to do this, is monday ok for you?:I:want to do this, is monday ok for you?"}, delimiter = ':')
    void whenExtractValueAllValuesAfterKeyword_ReturnValuesAfterKeyword(String line, String keyword, String expectedResult) {
        String result = ProcessLine.extractAllValuesAfterKeyword(line, keyword);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:1:is a beautiful day", "this is why I don't like monday:2:I don't like monday", "I want to do this, is monday ok for you?:0:want to do this, is monday ok for you?"}, delimiter = ':')
    void whenExtractValueAllValuesAfterIndex_ReturnValuesAfterIndex(String line, int index, String expectedResult) {
        String result = ProcessLine.extractAllValuesAfterIndex(line, index);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:1", "this is why I don't like monday:6", "I want to do this, is monday ok for you?:6"}, delimiter = ':')
    void whenExtractValue_ReturnValue(String line, int index) {
        String value = "monday";
        String result = ProcessLine.extractValue(line, index);
        assertThat(result).isEqualTo(value);
    }

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:120", "this is why I don't like monday:610", "I want to do this , is monday ok for you:764"}, delimiter = ':')
    void whenExtractSeveralValues_ReturnValues(String line, String index) {
        String value = "monday is this";
        String[] indexArr = index.split("");
        int[] n = new int[3];
        for (int i = 0; i < 3; i++) {
            n[i] = Integer.parseInt(indexArr[i]);
        }
        String result = ProcessLine.extractSeveralValues(line, n);
        assertThat(result).isEqualTo(value);
    }

    @Test
    void whenExtractValueOutOfReach_ReturnWordOutOfReachException() {
        String line = "this monday is a beautiful day";
        WordOutOfReachException wordOutOfReachException = assertThrows(WordOutOfReachException.class, () -> ProcessLine.extractValue(line, line.split("").length + 1));
        assertThat(wordOutOfReachException.getMessage()).isEqualTo(WORD_OUT_OF_REACH);
    }

    @ParameterizedTest
    @CsvSource(value = {"this monday is a beautiful day:this monday is a beautiful", "this is why I don't like monday:this is why I don't like", "I want to do this, is monday ok for you?:I want to do this, is monday ok for"}, delimiter = ':')
    void whenExcludeLastValue_ReturnLineWithoutLastValue(String line, String expectedResult) {
        String result = ProcessLine.excludeLastValue(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"the price is 12,99€:12.99", "1.732 is my height:1.732", "The field is 45,76 meters long:45.76"}, delimiter = ':')
    void whenExtractAmount_ReturnAmount(String line, Double expectedResult) {
        Double actualResult = ProcessLine.extractAmount(line);
        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(value = {"he began exercising at 12-04-3002:dd-MM-yyyy:1:4", "my school work is due on 06/11/1943:MM/dd/yyyy:1:6", "we broke up on 01-04-21:MM-dd-yy:1:1", "from 30-08-2020 to 06-10-2021 I was happy:dd-MM-yyyy:2:10"}, delimiter = ':')
    void whenExtractMonth_ReturnMonth(String line, String dateFormat, int iterations, int expectedResult) {
        Month expectedMonth = Month.of(expectedResult);
        Month actualResult = ProcessLine.extractMonth(line, dateFormat, iterations);
        assertThat(actualResult).isEqualTo(expectedMonth);
    }

    @ParameterizedTest
    @CsvSource(value = {"he began exercising at 12-04-3002:dd-MM-yyyy:1:3002", "my school work is due on 06/11/1943:MM/dd/yyyy:1:1943", "we broke up on 01-04-21:MM-dd-yy:1:2021", "from 30-08-2020 to 06-10-2021 I was happy:dd-MM-yyyy:2:2021"}, delimiter = ':')
    void whenExtractYear_ReturnYear(String line, String dateFormat, int iterations, int expectedResult) {
        Year expectedMonth = Year.of(expectedResult);
        Year actualResult = ProcessLine.extractYear(line, dateFormat, iterations);
        assertThat(actualResult).isEqualTo(expectedMonth);
    }

    @ParameterizedTest
    @CsvSource(value = {"he began exercising at 12-04-3002:dd-MM-yyyy:1:12:4:3002", "my school work is due on 06/11/1943:MM/dd/yyyy:1:11:6:1943", "we broke up on 01-04-21:MM-dd-yy:1:4:1:2021", "from 20.30.08 to 21.06.10 I was happy:yy.MM.dd:1:10:6:2021"}, delimiter = ':')
    void whenExtractDate_ReturnDate(String line, String dateFormat, int iterations, int expectedDay, int expectedMonth, int expectedYear) {
        LocalDate expectedDate = LocalDate.of(expectedYear, expectedMonth, expectedDay);
        LocalDate actualResult = ProcessLine.extractDate(line, dateFormat, iterations);
        assertThat(actualResult).isEqualTo(expectedDate);
    }
}