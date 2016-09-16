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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.minemung.placeholderengine.xkdfinal.XkdFinalProcessorB;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class EnemyTester {
    public static void main(String[] args) {
        Map<String, String> placeholder = new HashMap<>();
        placeholder.put("<50>", "aa");
        placeholder.put("<ab>", "bb");
        String a = "";

        for (int i = 0; i <= 100; i++) {
            if (i == 50) {
                a = a + "<<" + i + ">" + "asdf>";
            }
            a = a + "<" + i + ">";
        }
        XkdFinalProcessorB enemy = new XkdFinalProcessorB();
        enemy.prepare(a, placeholder);
        try (Writer writer = new FileWriter("EnemyOutput.json")) {
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            gson.toJson(enemy, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            enemy.process(a, placeholder);
        }
    }
}