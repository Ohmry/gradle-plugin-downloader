package io.ohmry.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {
    public static String find (String regex, String contents) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contents);

        if (!matcher.find()) {
            throw new NullPointerException();
        } else {
            return matcher.group(1);
        }
    }

    public static List<String> findAll (String regex, String contents) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(contents);

        List<String> findArray = new ArrayList<>();
        if (!matcher.find()) {
            throw new NullPointerException();
        }

        while (matcher.find()) {
            findArray.add(matcher.group(1));
        }
        return findArray;
    }
}
