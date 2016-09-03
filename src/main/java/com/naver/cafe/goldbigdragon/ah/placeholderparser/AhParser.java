package com.naver.cafe.goldbigdragon.ah.placeholderparser;

import com.naver.cafe.goldbigdragon.xkd.parsertester.AbstractParser;

import java.util.Map;

public class AhParser extends AbstractParser
{
    public String parse(String a, Map<String, String> placeholder)
    {
        StringBuilder s = new StringBuilder(a);
        String replace;
        int value = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            if (ch == '>') {
                value = i;
            }
            if (ch == '<') {
                replace =  placeholder.get(s.substring(i, value + 1));
                if (replace != null) {
                    s.replace(i, value + 1, replace);
                }
                int temp = s.length();
                for (int j = value; j < temp; j++) {
                    if (s.charAt(j) == '>') {
                        value = j;
                        break;
                    }
                }
            }
        }

        return s.toString();
    }
}
