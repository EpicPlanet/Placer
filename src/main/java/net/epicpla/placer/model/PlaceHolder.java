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

package net.epicpla.placer.model;

import net.epicpla.placer.Placer;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolder implements Component {
    List<Component> components;

    public PlaceHolder(List<Component> components) {
        this.components = components;
    }

    public PlaceHolder(String source) {
        components = new ArrayList<>();
        int depth = 0;
        StringBuilder builtString = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            final char character = source.charAt(i);
            switch (character) {
                case '<':
                    if (depth == 0) {
                        components.add(new StringComponent(builtString.toString()));
                        builtString = new StringBuilder();
                    }
                    depth++;
                    break;
                case '>':
                    if (depth == 1) {
                        components.add(new PlaceHolder(builtString.toString()));
                        builtString = new StringBuilder();
                    }
                    depth--;
                    break;
                default:
                    builtString.append(character);
                    break;
            }
        }
        if (depth == 0) {
            components.add(new StringComponent(builtString.toString()));
        } else if (depth == 1){
            components.add(new PlaceHolder(builtString.toString()));
        } else {
            throw new UnsupportedOperationException("Error in the source");
        }
    }

    @Override
    public String makeString() {
        StringBuilder builder = new StringBuilder();
        for (Component component : components) {
            builder.append(component.makeString());
        }
        return Placer.instance.getValue(builder.toString());
    }
}
