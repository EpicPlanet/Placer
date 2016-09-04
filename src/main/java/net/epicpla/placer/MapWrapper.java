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

/**
 * Created by final on 9/4/2016.
 */
public class MapWrapper {
    String[] map;
    int processed = 0;
    int size;

    final Object syncObject;
    public boolean ready = false;

    public MapWrapper(int size, Object syncObject) {
        map = new String[size];
        this.size = size;
        this.syncObject = syncObject;
    }

    public void put(int i, String text) {
        map[i] = text;
        processed ++;

        if (processed == size) {
            ready = true;
            synchronized(syncObject) {
                syncObject.notify();
            }
        }
    }

    public String buildString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i ++) {
            builder.append(map[i]);
        }
        return builder.toString();
    }
}
