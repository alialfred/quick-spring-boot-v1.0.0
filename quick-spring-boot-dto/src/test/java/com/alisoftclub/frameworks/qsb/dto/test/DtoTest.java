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
package com.alisoftclub.frameworks.qsb.dto.test;

import com.alisoftclub.frameworks.qsb.dto.pojo.Bar;
import com.alisoftclub.frameworks.qsb.dto.pojo.BarDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Ali Imran
 */
public class DtoTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws JsonProcessingException {
        Assert.assertNotNull(this.mapper);
        List<BarDto> list = new ArrayList<>(0);
        for (int i = 0; i < 1000; i++) {// 50000 = 1168 ms
            Bar bar = new Bar();
            bar.setId(1L);
            bar.setTitle("Bat Title");
            bar.setRate(10);
            bar.setQty(10);
            BarDto dto = new BarDto(bar);
            list.add(dto);
        }
        long start = System.currentTimeMillis();
        String json = mapper.writeValueAsString(list);
        long end = System.currentTimeMillis();
        float consumed = (end - start) / 1000F;
        System.out.println("Json: " + json);
        System.out.println("Converting Time: " + (end - start));
        System.out.println("Converting Time: " + consumed);
    }

    @Test
    public void test2() throws JsonProcessingException {
        Assert.assertNotNull(this.mapper);
        Bar bar = new Bar();
        bar.setId(1L);
        bar.setTitle("Bat Title");
        bar.setRate(10);
        bar.setQty(10);
        BarDto dto = new BarDto(bar);
        Map m = dto.getValues();
        System.out.println(m);
        String json = mapper.writeValueAsString(dto);
        System.out.println(json);
    }

    @Test
    public void test3() throws JsonProcessingException {
        Assert.assertNotNull(this.mapper);
        Bar bar = new Bar();
        bar.setId(1L);
        bar.setTitle(null);
        bar.setRate(10);
        bar.setQty(10);
        BarDto dto = new BarDto(bar);
        Map m = dto.getValues();
        System.out.println(m);
        String json = mapper.writeValueAsString(dto);
        System.out.println(json);
    }
}
