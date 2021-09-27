/*
 * Copyright 2018 Ali Imran.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alisoftclub.frameworks.qsb.common;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Ali Imran
 */
public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean equals(float o1, float o2) {
        return o1 == o2;
    }

    public static boolean notEquals(float o1, float o2) {
        return o1 != o2;
    }

    public static boolean equals(Object o1, Object o2) {
        return Objects.equals(o1, o2);
    }

    public static boolean notEquals(Object o1, Object o2) {
        return !equals(o1, o2);
    }

    public static boolean isEmpty(String string) {
        return isNull(string) || string.trim().isEmpty();
    }

    public static boolean notEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean not(boolean b) {
        return !b;
    }

    public static boolean isNull(Object o1) {
        return o1 == null;
    }

    public static boolean notNull(Object o1) {
        return o1 != null;
    }

    public static <T> T ifNull(T value, T def) {
        return notNull(value) ? value : def;
    }

    public static String ifEmpty(String value, String def) {
        return notEmpty(value) ? value : def;
    }

    public static boolean notZero(float number) {
        return number != 0;
    }

    public static String trimCharacters(String string, char... chars) {
        StringBuilder buffer = new StringBuilder();
        for (char c : string.toCharArray()) {
            boolean add = true;
            for (char cc : chars) {
                if (c == cc) {
                    add = false;
                    break;
                }
            }
            if (add) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static String normalizeLong(String number) {
        StringBuilder buffer = new StringBuilder();
        for (char c : number.toCharArray()) {
            if (Character.isDigit(c)) {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public static String normalizeDouble(String number) {
        List<String> list = Arrays.asList(number.split("\\."));
        if (list.isEmpty()) {
            return "";
        }
        String s = normalizeLong(list.get(0));
        if (list.size() == 2) {
            s += "." + normalizeLong(list.get(1));
        }
        return s;
    }
}
