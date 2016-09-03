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
        placeholder.put("<50>", "aa");
        placeholder.put("<ab>", "bb");
    }

    public static void main(final String[] args) {
        StringBuilder passedStringBuilder = new StringBuilder("");
        for (int i = 0; i <= 100; i++) {
            if (i == 50) {
                passedStringBuilder.append("<<").append(i).append(">asdf>");
            }
            passedStringBuilder.append("<").append(i).append(">");
        }

        String passedString = passedStringBuilder.toString();
        System.out.println(passedStringBuilder);

        long start = System.currentTimeMillis();
        for (int tt = 0; tt < 100000; tt ++) {
            StringBuilder builder = new StringBuilder(passedString);
            int lastHolderStart = 0;
            for (int i = builder.length() - 1; i >= 0; i --) {
                char ch = builder.charAt(i);
                if (ch == '>') {
                    lastHolderStart = i;
                } else if (ch == '<') {
                    String place = builder.substring(i, lastHolderStart + 1);
                    if(placeholder.containsKey(place)) {
                        String replaced = placeholder.get(place);
                        builder.replace(i, lastHolderStart + 1, replaced);
                        int temp = builder.length();
                        for (int j = i + replaced.length(); j < temp; j ++) {
                            if (builder.charAt(j) == '>') {
                                lastHolderStart = j;
                                break;
                            }
                        }
                    } else {
                        int temp = builder.length();
                        for (int j = lastHolderStart + 1; j < temp; j ++) {
                            if (builder.charAt(j) == '>') {
                                lastHolderStart = j;
                                break;
                            }
                        }
                    }
                }
            }
            String result = builder.toString();
            if (tt == 99999) System.out.println(result);
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}