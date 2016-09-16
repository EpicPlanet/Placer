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

package net.epcipla.placer.test;

import net.epicpla.placer.ValueProvider;

import java.util.HashMap;
import java.util.Map;

public class TestValueProvider implements ValueProvider {

    private Map<String, String> placeholders = new HashMap<>();

    public TestValueProvider(Map<String, String> originalPlaceHolders) {
        for (Map.Entry<String, String> entry : originalPlaceHolders.entrySet()) {
            String key = entry.getKey();
            placeholders.put(key.substring(1, key.length() - 1), entry.getValue());
        }
    }

    @Override
    public boolean hasValue(String key) {
        return placeholders.containsKey(key);
    }

    @Override
    public String getValue(String key) {
        if (placeholders.containsKey(key)) {
            return placeholders.get(key);
        } else {
            return "<" + key + ">";
        }
    }

    @Override
    public void getValueAndAppend(String key, StringBuilder builder) {
        if (placeholders.containsKey(key)) {
            builder.append(placeholders.get(key));
        } else {
            builder.append("<").append(key).append(">");
        }
    }
}
