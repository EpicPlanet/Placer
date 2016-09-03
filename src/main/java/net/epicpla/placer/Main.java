/*
 * This file is part of Placer, licensed under the MIT License (MIT).
 *
 * Copyright (c) Epic Planet Minecraft Server <https://epicpla.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package net.epicpla.placer;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Map<String, String> placeholder = new HashMap<>();

    static {
        placeholder.put("50", "aa");
        placeholder.put("ab", "bb");
    }

    public static void main(final String[] args) {
        final StringBuilder passedStringBuilder = new StringBuilder("");
        for (int i = 0; i <= 100; i++) {
            if (i == 50) {
                passedStringBuilder.append("<<").append(i).append(">asdf>");
            }
            passedStringBuilder.append("<").append(i).append(">");
        }

        final String passedString = passedStringBuilder.toString();
        System.out.println(passedStringBuilder);

        long start = System.currentTimeMillis();
        for (int tt = 0; tt < 100000; tt ++) {
            final String result = parse(passedString);
            if (tt == 0) System.out.println(result);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    public static String parse(final String passedString) {
        final StringBuilder builder = new StringBuilder(passedString);
        int lastHolderStart = -1;
        int startSearchFrom = -1;
        for (int i = builder.length() - 1; i >= 0; i --) {
            switch (builder.charAt(i)) {
                case '>':
                    lastHolderStart = i;
                    break;
                case '<':
                    if (lastHolderStart == -1) {
                        lastHolderStart = getLastHolderStart(startSearchFrom, builder);
                    }
                    final String place = builder.substring(i + 1, lastHolderStart);
                    if(placeholder.containsKey(place)) {
                        final String replaced = placeholder.get(place);
                        builder.replace(i, lastHolderStart + 1, replaced);

                        startSearchFrom = i + replaced.length();
                        lastHolderStart = -1;
                    } else {
                        startSearchFrom = lastHolderStart + 1;
                        lastHolderStart = -1;
                    }
                    break;
            }

        }
        return builder.toString();
    }

    public static int getLastHolderStart(final int searchFrom, final StringBuilder builder) {
        final int temp = builder.length();
        for (int i = searchFrom; i < temp ; i ++) {
            if (builder.charAt(i) == '>') return i;
        }

        return -1;
    }
}