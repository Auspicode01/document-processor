package org.auspicode.cml;

import lombok.NonNull;
import org.auspicode.cml.exception.WordOutOfReachException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.auspicode.cml.exception.ErrorMessages.WORD_OUT_OF_REACH;

public class ProcessLine {

    public static String extractValueAfterKeyword(@NonNull String line, String keyword, int index) {
        String[] lineAfterKeyword = line.split(keyword);
        String[] splitLine = lineAfterKeyword[1].trim().split(" ");
        checkWordReach(splitLine, index);
        return splitLine[index].trim();
    }

    public static String extractValue(@NonNull String line, int index) {
        String[] splitLine = line.trim().split(" ");
        checkWordReach(splitLine, index);
        return splitLine[index].trim();
    }

    public static String extractSeveralValues(@NonNull String line, int... index) {
        String result = "";
        String[] splitLine = line.trim().split(" ");
        for (int n : index) {
            checkWordReach(splitLine, n);
            result = result.concat(splitLine[n].trim()).concat(" ");
        }
        return result.trim();
    }

    private static void checkWordReach(String[] wordArr, int n) {
        if (n > wordArr.length) {
            throw new WordOutOfReachException(WORD_OUT_OF_REACH);
        }
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
        Pattern pattern = Pattern.compile(Constants.EXTRACT_DATE_REGEX_PATTERN);
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
