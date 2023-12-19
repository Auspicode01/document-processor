package org.auspicode.cml.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private static final String DELIMITER = "[^\\w\\d\\r\\n:]";

    private static final String YEAR_VALUES = "(\\d{4}|\\d{2})";

    private static final String MONTH_VALUES = "(0?[1-9]|1[0-2])";

    private static final String DAY_VALUES = "(0?[1-9]|[12]\\d|30|31)";

    private static final String BOUNDARY_TAG = "\\b";

    public static String createDateRegexPattern(String dateFormat) {
        if (dateFormat.startsWith("dd")) {
            return BOUNDARY_TAG.concat(DAY_VALUES).concat(DELIMITER).concat(MONTH_VALUES).concat(DELIMITER).concat(YEAR_VALUES).concat(BOUNDARY_TAG);
        } else if (dateFormat.startsWith("yy")) {
            return BOUNDARY_TAG.concat(YEAR_VALUES).concat(DELIMITER).concat(MONTH_VALUES).concat(DELIMITER).concat(DAY_VALUES).concat(BOUNDARY_TAG);
        } else {
            return BOUNDARY_TAG.concat(MONTH_VALUES).concat(DELIMITER).concat(DAY_VALUES).concat(DELIMITER).concat(YEAR_VALUES).concat(BOUNDARY_TAG);
        }
    }
}
