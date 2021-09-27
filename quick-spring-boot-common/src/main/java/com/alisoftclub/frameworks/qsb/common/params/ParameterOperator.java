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
package com.alisoftclub.frameworks.qsb.common.params;

/**
 *
 * @author Ali Imran
 */
public enum ParameterOperator {
    BETWEEN("between"),
    EQUAL("="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    IN("in"),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<="),
    LIKE("like"),
    NOT_IN("nin"),
    NOT_EQUAL("!="),
    START_WITH("like"),
    END_WITH("like"),
    ;
    private final String op;

    private ParameterOperator(String op) {
        this.op = op;
    }

    @Override
    public String toString() {
        return String.format(" %s ", op);
    }

    public static ParameterOperator getOperator(String code) {
        switch (code) {
            case "between":
                return BETWEEN;
            case "gt":
                return GREATER_THAN;
            case "gteq":
                return GREATER_THAN_OR_EQUAL;
            case "in":
                return IN;
            case "lt":
                return LESS_THAN;
            case "lteq":
                return LESS_THAN_OR_EQUAL;
            case "like":
                return LIKE;
            case "ne":
                return NOT_EQUAL;
            case "nin":
                return NOT_IN;
            case "startWith":
                return START_WITH;
            case "endWith":
                return END_WITH;
        }
        return EQUAL;
    }

}
