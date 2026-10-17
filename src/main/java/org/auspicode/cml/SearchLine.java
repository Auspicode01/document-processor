package org.auspicode.cml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.auspicode.cml.exception.OffsetIsZeroException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Optional;

import static org.auspicode.cml.exception.ErrorMessages.OFFSET_IS_ZERO;
import static org.auspicode.cml.exception.ErrorMessages.TEXT_KEYWORD_NOT_NULL;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchLine {

    public static Optional<String> withOffset(String text, String keyword, int offset) {
        if (offset == 0) {
            throw new OffsetIsZeroException(OFFSET_IS_ZERO);
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            List<String> lines = reader.lines().toList();

            int keywordIndex = -1;
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(keyword)) {
                    keywordIndex = i;
                    break;
                }
            }

            if (keywordIndex == -1) {
                return Optional.empty();
            }

            int targetIndex = keywordIndex + offset;
            if (targetIndex >= 0 && targetIndex < lines.size()) {
                return Optional.of(lines.get(targetIndex));
            }

            return Optional.empty();

        } catch (IOException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<String> withKeyword(String text, String keyword) {
        if (text == null || keyword == null) {
            throw new IllegalArgumentException(TEXT_KEYWORD_NOT_NULL);
        }

        try (BufferedReader reader = new BufferedReader(new StringReader(text))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(keyword)) {
                    return Optional.of(line);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
