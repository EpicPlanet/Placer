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

import net.epicpla.placer.MapWrapper;
import net.epicpla.placer.Placer;

import java.util.ArrayList;
import java.util.List;

public class PlaceHolder implements Component {

    public Placer placer;
    public List<Component> components;

    public int current;

    // 나중에 전처리 성능 개선 목적으로 쓰일 경우를 대비해서 만들어둠
    public PlaceHolder(List<Component> components, Placer placer) {
        this.components = components;
        this.placer = placer;
    }

    public PlaceHolder(String source, Placer placer) {
        this.placer = placer;
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
                        components.add(new PlaceHolder(builtString.toString(), placer));
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
            components.add(new PlaceHolder(builtString.toString(), placer));
        } else {
            throw new UnsupportedOperationException("Error in the source");
        }
    }

    @Override
    public String makeString() {
        int size = components.size();
        Object syncObject = new Object();
        MapWrapper componentStrings = new MapWrapper(size, syncObject);

        for (int i = 0; i < size; i ++) {
            int index = i;
            Thread thread = new Thread(() -> {
                componentStrings.put(index, components.get(index).makeString());
            });

            thread.start();
        }

        synchronized (syncObject) {
            while (!componentStrings.ready) {
                try {
                    syncObject.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return placer.getValue(componentStrings.buildString());
        }
    }

    public boolean isSimple() {
        return components.size() == 1 && components.get(0) instanceof StringComponent;
    }

    public void simplifyFakes() {
        for (int i = 0; i < components.size(); i ++) {
            Component component = components.get(i);
            if (component instanceof PlaceHolder) { // component가 PlaceHolder이면
                ((PlaceHolder) component).simplifyFakes(); // 아래 것들을 모두 정리
                if (((PlaceHolder) component).isSimple()) { // 간단하고
                    if (!placer.hasValue(((PlaceHolder) component).components.get(0).makeString())) { // 그 값이 없으면
                        components.set(i, new StringComponent("<" + ((PlaceHolder) component).components.get(0).makeString() + ">"));
                    }
                }
            }
        }

        boolean wasLastString = false;
        List<Component> newComponents = new ArrayList<>();
        for (Component component : components) {
            if (component instanceof StringComponent) {
                if (wasLastString) {
                    int lastIndex = newComponents.size() - 1;
                    newComponents.set(lastIndex, new StringComponent(newComponents.get(lastIndex).makeString() + component.makeString()));
                } else {
                    wasLastString = true;
                    newComponents.add(component);
                }
            } else {
                wasLastString = false;
                newComponents.add(component);
            }
        }
        components = newComponents;
    }
}
