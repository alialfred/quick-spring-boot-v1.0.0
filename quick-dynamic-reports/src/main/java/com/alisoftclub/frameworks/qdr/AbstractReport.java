/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qdr;

import com.alisoftclub.frameworks.qsb.common.exception.ApiException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import org.springframework.http.HttpStatus;

/**
 *
 * @author AI
 */
public abstract class AbstractReport {

    protected static final Map<String, String> mediaType = new HashMap(0);

    static {
        if (mediaType.isEmpty()) {
            mediaType.put("csv", "text/csv");
            mediaType.put("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            mediaType.put("pdf", "application/pdf");
            mediaType.put("xls", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            mediaType.put("xlsx", mediaType.get("xls"));
        }
    }

    protected ReportBuilder builder;
    protected JasperReportBuilder report;
    private final String title;
    private boolean built = false;

    public AbstractReport(String title, ReportBuilder builder) {
        this.title = title;
        this.builder = builder;
        init();
    }

    private void init() {
        this.report = report();
    }

    public abstract void build();

    public String getTitle() {
        return title;
    }

    public String getMedaType(String type) {
        return mediaType.get(type);
    }

    public String print(List<?> data, OutputStream outStream) throws ApiException {
        return print("pdf", data, outStream);
    }

    public String print(String type, List<?> data, OutputStream outStream) throws ApiException {
        try {
            String t = type;
            report = report.setDataSource(data).rebuild().setIgnorePagination(Boolean.TRUE);
            switch (type) {
                case "csv": {
                    report.toCsv(outStream);
                    break;
                }
                case "doc":
                    report.toRtf(outStream);
                    break;
                case "docx": {
                    report.toDocx(outStream);
                    break;
                }
                case "html": {
                    report.toHtml(outStream);
                    break;
                }
                case "jasper": {
//                    report.toJasper;
                    break;
                }
                case "xls":
                    report.toXls(outStream);
                    break;
                case "xlsx": {
                    report.toXlsx(outStream);
                    break;
                }
                default: {
                    t = "pdf";
                    report.setIgnorePagination(Boolean.FALSE).toPdf(outStream);
                }
            }
            return t;
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex);
        }
    }

    public JasperReportBuilder getReport() {
        return report;
    }
}
