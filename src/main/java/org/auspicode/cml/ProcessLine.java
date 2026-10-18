package org.auspicode.cml;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.auspicode.cml.utils.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessLine {

    public static Optional<String> extractValueAfterKeyword(@NonNull String line, String keyword, int index) {
        String[] lineAfterKeyword = line.split(keyword);
        String[] splitLine = lineAfterKeyword[1].trim().split(" ");
        return isWordReachable(splitLine, index) ? Optional.of(splitLine[index].trim()) : Optional.empty();
    }

    public static Optional<String> extractAllValuesAfterKeyword(@NonNull String line, String keyword) {
        String result = "";
        String[] lineAfterKeyword = line.split(keyword);
        String[] splitLine = lineAfterKeyword[1].trim().split(" ");
        for (String str : splitLine) {
            result = result.concat(str.trim()).concat(" ");
        }
        return Optional.of(result.trim());
    }

    public static Optional<String> extractAllValuesAfterIndex(@NonNull String line, int index) {
        String result = "";
        String[] splitLine = line.trim().split(" ");
        for (int i = index + 1; i < splitLine.length; i++) {
            result = result.concat(splitLine[i].trim()).concat(" ");
        }
        return Optional.of(result.trim());
    }

    public static Optional<String> extractValue(@NonNull String line, int index) {
        String[] splitLine = line.trim().split(" ");
        return isWordReachable(splitLine, index) ? Optional.of(splitLine[index].trim()) : Optional.empty();
    }

    public static Optional<String> extractSeveralValues(@NonNull String line, int... index) {
        String result = "";
        String[] splitLine = line.trim().split(" ");
        for (int n : index) {
            result = isWordReachable(splitLine, n) ? result.concat(splitLine[n].trim()).concat(" ") : result;
        }
        return Optional.of(result.trim());
    }

    public static String excludeLastValue(@NonNull String line) {
        String result = "";
        String[] splitLine = line.trim().split(" ");
        for (int i = 0; i < splitLine.length - 1; i++) {
            result = result.concat(splitLine[i].trim()).concat(" ");
        }
        return result.trim();
    }

    private static boolean isWordReachable(String[] wordArr, int n) {
        return n < wordArr.length;
    }

    public static Double extractAmount(@NonNull String line) {
        String result = "";
        Pattern pattern = Pattern.compile(Constants.EXTRACT_AMOUNT_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            result = matcher.group().contains(",") ? matcher.group().replace(",", ".") : matcher.group();
        }
        return Double.valueOf(result);
    }

    // Extract Timestamps
    public static Month extractMonth(String line, String dateFormat, int iterations) {
        return Month.of(extractCalendar(line, dateFormat, iterations).get(Calendar.MONTH) + 1);
    }

    public static Year extractYear(String line, String dateFormat, int iterations) {
        return Year.of(extractCalendar(line, dateFormat, iterations).get(Calendar.YEAR));
    }

    public static LocalDate extractDate(String line, String dateFormat, int iterations) {
        return LocalDate.ofInstant(extractCalendar(line, dateFormat, iterations).toInstant(), ZoneId.systemDefault());
    }

    private static Calendar extractCalendar(@NonNull String line, String dateFormat, int iterations) {
        Pattern pattern = Pattern.compile(DateUtils.createDateRegexPattern(dateFormat));
        Matcher matcher = pattern.matcher(line);
        for (int i = 0; i < iterations; i++) {
            matcher.find();
        }
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(new SimpleDateFormat(dateFormat, Locale.ENGLISH).parse(matcher.group()));
        } catch (ParseException e) {
            System.out.println(e.getErrorOffset());
        }
        return cal;
    }
}
