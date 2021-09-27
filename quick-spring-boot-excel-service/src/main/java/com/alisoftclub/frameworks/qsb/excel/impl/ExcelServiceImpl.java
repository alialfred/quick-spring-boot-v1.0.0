/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.excel.impl;

import com.alisoftclub.frameworks.qsb.excel.ExcelService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl implements ExcelService {

    @Override
    public <T> List<T> importWorkbook(Workbook workBook, BiFunction<Row, Sheet, T> converter) throws IOException {
        List<T> list = new ArrayList(0);
        for (int i = 0; i < workBook.getNumberOfSheets(); i++) {
            Sheet sheet = workBook.getSheetAt(i);
            sheet.rowIterator().forEachRemaining(row -> {
                list.add(converter.apply(row, sheet));
            });
        }
        list.removeIf(Objects::isNull);
        return list;
    }

    @Override
    public <T> List<T> importCsv(InputStream inStream, Function<String[], T> converter) throws IOException {
        List<T> list = new ArrayList(0);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String line;
        while ((line = reader.readLine()) != null) {
            String parts[] = line.split(",");
            list.add(converter.apply(parts));
        }
        list.removeIf(Objects::isNull);
        return list;
    }

}
