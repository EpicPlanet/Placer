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

import net.epicpla.placer.Placer;

import java.util.HashMap;
import java.util.Map;

public class tester {

    public static void main(String[] args) {
        String a = "";
        for (int i = 0; i <= 100; i++) {
            if (i == 50) {
                a = a + "<<50>asdf>";
            }
            a = a + "<" + i + ">";
        }
        Map<String, String> placeholder = new HashMap<String, String>();
        placeholder.put("<50>", "aa");
        placeholder.put("<ab>", "bb");

        Placer fc = new Placer();
        fc.prepare(new String(a), placeholder);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            fc.parse(a, placeholder);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

}
