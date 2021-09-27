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
package com.alisoftclub.frameworks.qsb.dto.pojo;

import com.alisoftclub.frameworks.qsb.dto.DefaultDto;
import com.alisoftclub.frameworks.qsb.dto.DtoPropertyBuilder;

/**
 *
 * @author Ali Imran
 */
public class BarDto extends DefaultDto<Bar> {

    public static final DtoPropertyBuilder BUILDER = DtoPropertyBuilder.create()
            .add("id")
            .add("title", "barName")
            .add("rate")
            .add("qty")
            .add("rate * qty", "amount") //
            ;

    public BarDto() {
        this(new Bar());
    }

    public BarDto(Bar entity) {
        super(entity, BUILDER);
    }

}
