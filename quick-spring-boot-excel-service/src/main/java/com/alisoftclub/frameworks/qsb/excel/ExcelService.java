package com.alisoftclub.frameworks.qsb.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author AI
 */
@SuppressWarnings("unused")
public interface ExcelService {

    @SuppressWarnings("unchecked")
    default <T> List<T> importExcel(MultipartFile file, BiFunction<Row, Sheet, T> converter) throws IOException {

        String fn = Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().split("\\.")[1];
        switch (fn) {
//            case "csv":
//                return this.importCsv(file.getInputStream(), converter);
            case "xls":
                return this.importExcel(file.getInputStream(), converter);
            case "xlsx":
                return this.importExcelX(file.getInputStream(), converter);
        }
        return Collections.EMPTY_LIST;
    }

    default <T> List<T> importExcel(InputStream inStream, BiFunction<Row, Sheet, T> converter) throws IOException {
        return this.importWorkbook(new HSSFWorkbook(inStream), converter);
    }

    default <T> List<T> importExcelX(InputStream inStream, BiFunction<Row, Sheet, T> converter) throws IOException {
        return this.importWorkbook(new XSSFWorkbook(inStream), converter);
    }

    <T> List<T> importWorkbook(Workbook workBook, BiFunction<Row, Sheet, T> converter) throws IOException;

    <T> List<T> importCsv(InputStream inStream, Function<String[], T> converter) throws IOException;
}
