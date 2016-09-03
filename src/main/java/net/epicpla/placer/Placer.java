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

import com.naver.cafe.goldbigdragon.xkd.parsertester.AbstractParser;
import net.epicpla.placer.model.Root;

import java.util.HashMap;
import java.util.Map;

public class Placer extends AbstractParser {

    public static Placer instance;
    public Root holder;
    public Map<String, String> placeholders = new HashMap<>();

    public Placer() {
        instance = this;
    }

    @Override
    public boolean prepare(String s, Map<String, String> placeholder_original) {
        for (String key : placeholder_original.keySet()) {
            placeholders.put(key.substring(1, key.length() - 1), placeholder_original.get(key));
        }
        holder = new Root(s);
        holder.simplifyFakes();
        return true;
    }

    @Override
    public String parse(String source, Map<String, String> placeholder) {
        return holder.makeString();
    }

    public String getValue(String placeholder) {
        if (placeholders.containsKey(placeholder)) {
            return placeholders.get(placeholder);
        } else {
            return "<" + placeholder + ">";
        }
    }

    public boolean hasValue(String placeholder) {
        return placeholders.containsKey(placeholder);
    }
}
