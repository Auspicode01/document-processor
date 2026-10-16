package org.auspicode.cml;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchValue {

    public static List<String> withRegex(final String text, final String regexPattern) {
        List<String> matches = new ArrayList<>();

        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }
}
