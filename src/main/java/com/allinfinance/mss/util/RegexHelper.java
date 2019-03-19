package com.allinfinance.mss.util;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHelper {

    private RegexHelper() {
    }

    public static final String getStrBetweenKeywords(String text, String keyword1, String keyword2) {
        Pattern p = Pattern.compile(new StringBuffer()
                        .append("(").append(keyword1).append(")")
                        .append("(.+)")
                        .append("(").append(keyword2).append(")")
                        .toString(),
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            return m.group(2);
        }
        return null;
    }

    public static final String[] getLazyStrBetweenKeywords(String text, String keyword1, String keyword2) {
        String[] ret = new String[0];
        Set<String> tmp = new HashSet<String>();
        Pattern p = Pattern.compile(new StringBuffer()
                        .append("(").append(keyword1).append(")")
                        .append("(.+?)")
                        .append("(").append(keyword2).append(")")
                        .toString(),
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(text);
        while (m.find()) {
            tmp.add(m.group(2));
        }
        return tmp.toArray(ret);
    }

    public static final boolean isMatch(String text, String reg) {
        if (StringUtils.isNotEmpty(text) && StringUtils.isNotBlank(reg)) {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(text);
            return m.matches();
        }
        return false;
    }

    public static final boolean isContain(String text, String reg) {
        if (StringUtils.isNotEmpty(text) && StringUtils.isNotBlank(reg)) {
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(text);
            return m.find();
        }
        return false;
    }

    public static final boolean isDigitals(String message) {
        return RegexHelper.isMatch(message, "^[0-9]+$");
    }
}
