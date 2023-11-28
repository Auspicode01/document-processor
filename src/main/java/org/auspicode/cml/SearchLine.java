package org.auspicode.cml;

import org.auspicode.cml.exception.KeywordNotFoundException;
import org.auspicode.cml.exception.LineOutOfReachException;
import org.auspicode.cml.exception.OffsetIsZeroException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.auspicode.cml.exception.ErrorMessages.*;

public class SearchLine {

    public static String withOffset(String text, String keyword, int offset) {
        if (offset == 0) {
            throw new OffsetIsZeroException(OFFSET_IS_ZERO);
        }
        String line = "";
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            int keywordLine = findKeywordLine(reader, text, keyword);
            if (offset > 0) {
                line = findByAmountOfLines(reader, text, offset);
            } else {
                reader.reset();
                line = findByAmountOfLines(reader, text, keywordLine + offset);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        if (line == null) {
            throw new LineOutOfReachException(LINE_OUT_OF_REACH);
        }
        return line;
    }

    private static String findByAmountOfLines(BufferedReader reader, String text, int amountOfLines) throws IOException {
        for (int i = 1; i < amountOfLines; i++) {
            reader.readLine();
        }
        return reader.readLine();
    }

    private static int findKeywordLine(BufferedReader reader, String text, String keyword) throws IOException {
        int numberOfLinesRead = 1;
        String line = "";
        reader.mark(1);
        line = reader.readLine();
        while (!line.contains(keyword)) {
            line = reader.readLine();
            numberOfLinesRead++;
        }
        if (null == line) {
            throw new KeywordNotFoundException(KEYWORD_NOT_FOUND);
        }
        return numberOfLinesRead;
    }

    public static String withKeyword(String text, String keyword) {
        String line = "";
        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            line = reader.readLine();
            while (!line.contains(keyword)) {
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        try {
            line.contains(keyword);
        } catch (NullPointerException e) {
            throw new KeywordNotFoundException(KEYWORD_NOT_FOUND);
        }
        return line;
    }

}
