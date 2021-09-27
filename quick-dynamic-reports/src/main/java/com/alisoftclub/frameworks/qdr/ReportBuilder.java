/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qdr;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.definition.expression.DRIValueFormatter;

/**
 *
 * @author AI
 */
public class ReportBuilder {

    private final BiFunction<String, ReportBuilder, BaseReport> supplier;
    private final Map<String, TextColumnBuilder<?>> columns = new LinkedHashMap(0);
    private final Map<String, AggregationSubtotalBuilder<?>> subtotals = new LinkedHashMap(0);
    private final Map<String, Group> groups = new LinkedHashMap(0);
    private final String reportTitle;
    private boolean header = true, footer = true;

    public static ReportBuilder create() {
        return create(BaseReport::new);
    }

    public static ReportBuilder create(String reportTitle) {
        return create(reportTitle, BaseReport::new);
    }

    public static ReportBuilder create(BiFunction<String, ReportBuilder, BaseReport> supplier) {
        return create(null, supplier);
    }

    public static ReportBuilder create(String reportTitle, BiFunction<String, ReportBuilder, BaseReport> supplier) {
        return new ReportBuilder(reportTitle, supplier);
    }

    private ReportBuilder(String reportTitle, BiFunction<String, ReportBuilder, BaseReport> supplier) {
        this.reportTitle = reportTitle;
        this.supplier = supplier;
    }

    public ReportBuilder add(String title) {
        return this.add(title, String.class);
    }

    public ReportBuilder add(String title, Class<?> clazz) {
        return this.add(title, title, clazz);
    }

    public ReportBuilder add(String title, String field) {
        return this.add(title, field, String.class);
    }

    public ReportBuilder add(String title, String field, Class<?> clazz) {
        return this.add(field, col.column(title, field, clazz));
    }

    public ReportBuilder add(String name, TextColumnBuilder<?> column) {
        this.columns.put(name, column);
        return this;
    }

    public ReportBuilder pattern(String pattern, String... columns) {
        for (String name : columns) {
            TextColumnBuilder<?> column = this.columns.get(name);
            if (Objects.nonNull(column)) {
                column.setPattern(pattern);
            }
        }
        return this;
    }

    public ReportBuilder pattern(DRIExpression<String> pattern, String... columns) {
        for (String name : columns) {
            TextColumnBuilder<?> column = this.columns.get(name);
            if (Objects.nonNull(column)) {
                column.setPattern(pattern);
            }
        }
        return this;
    }

    public ReportBuilder valueFormat(DRIValueFormatter valueFormatter, String... columns) {
        for (String name : columns) {
            TextColumnBuilder<?> column = this.columns.get(name);
            if (Objects.nonNull(column)) {
                column.setValueFormatter(valueFormatter);
            }
        }
        return this;
    }

    public ReportBuilder sum(String... names) {
        for (String name : names) {
            if (this.columns.containsKey(name)) {
                this.subtotals.put(name, sbt.sum((ValueColumnBuilder) this.columns.get(name)));
            }
        }

        return this;
    }

    public ReportBuilder avg(String... names) {
        for (String name : names) {
            if (this.columns.containsKey(name)) {
                this.subtotals.put(name, sbt.avg((ValueColumnBuilder) this.columns.get(name)));
            }
        }

        return this;
    }

    public ReportBuilder count(String... names) {
        for (String name : names) {
            if (this.columns.containsKey(name)) {
                this.subtotals.put(name, sbt.count((ValueColumnBuilder) this.columns.get(name)));
            }
        }

        return this;
    }

    public ReportBuilder group(String... names) {
        for (String name : names) {
            if (this.columns.containsKey(name)) {
                ColumnGroupBuilder itemGroup = grp.group(this.columns.get(name))
                        //                        .setTitleWidth(30)
                        //                        .showColumnHeaderAndFooter()
                        .setHeaderLayout(GroupHeaderLayout.VALUE)//
                        ;
                this.groups.put(name, new Group(itemGroup));
            }
        }

        return this;
    }

    public ReportBuilder groupSumHeader(String groupName, String... columns) {
        if (this.groups.containsKey(groupName)) {
            Group group = this.groups.get(groupName);
            List<AggregationSubtotalBuilder<?>> list = new LinkedList();
            for (String name : columns) {
                if (this.columns.containsKey(name)) {
                    list.add(sbt.sum((ValueColumnBuilder) this.columns.get(name)));
                }
            }
            group.setSubtotalsHeader(list);
        }
        return this;
    }

    public ReportBuilder groupSumFooter(String groupName, String... columns) {
        if (this.groups.containsKey(groupName)) {
            Group group = this.groups.get(groupName);
            List<AggregationSubtotalBuilder<?>> list = new LinkedList();
            for (String name : columns) {
                if (this.columns.containsKey(name)) {
                    list.add(sbt.sum((ValueColumnBuilder) this.columns.get(name)));
                }
            }
            group.setSubtotalsFooter(list);
        }
        return this;
    }

    public ReportBuilder text(String name, String text) {
        if (this.columns.containsKey(name)) {
            this.subtotals.put(name, sbt.text(text, this.columns.get(name)));
        }
        return this;
    }

    public ReportBuilder footer(boolean footer) {
        this.footer = footer;
        return this;
    }

    public ReportBuilder header(boolean header) {
        this.header = header;
        return this;
    }

    Collection<TextColumnBuilder<?>> getColumns() {
        return columns.values();
    }

    Collection<AggregationSubtotalBuilder<?>> getSubtotals() {
        return subtotals.values();
    }

    Collection<Group> getGroups() {
        return groups.values();
    }

    public BaseReport buildReport() {
        return this.buildReport(this.supplier);
    }

    public <T extends BaseReport> T buildReport(BiFunction<String, ReportBuilder, T> supplier) {
        T report = supplier.apply(this.reportTitle, this);
        report.setFooter(footer);
        report.setHeader(header);
        report.build();
        return report;
    }
}
