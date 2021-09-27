/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qdr;

import com.alisoftclub.frameworks.qdr.templates.Templates;
import java.util.Objects;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import net.sf.dynamicreports.report.builder.component.VerticalListBuilder;
import net.sf.dynamicreports.report.builder.subtotal.SubtotalBuilder;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;

/**
 *
 * @author AI
 */
public class BaseReport extends AbstractReport {

    private boolean footer = true;
    private boolean header = true;

    public BaseReport(String title, ReportBuilder builder) {
        super(title, builder);
    }

    public boolean isFooter() {
        return footer;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public boolean isHeader() {
        return header;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public void addHeader() {
        report.title(Templates.createTitleComponent(this.getTitle()));
    }

    public void addFooter() {
        report.pageFooter(Templates.footerComponent);
    }

    @Override
    public void build() {
        report.setTemplate(Templates.reportTemplate);
        if (this.header) {
            this.addHeader();
        }
        if (this.footer) {
            this.addFooter();
        }
        if (Objects.nonNull(this.builder)) {
            this.builder.getColumns().forEach(report::addColumn);
            this.builder.getSubtotals().forEach(report::addSubtotalAtSummary);
            this.builder.getGroups().forEach(this::addGroup);
        }
    }

    public VerticalListBuilder titleComponent(String title, DRIExpression<String> textExpression) {
        return cmp.verticalList(
                cmp.text(title).setStyle(Templates.columnTitleStyle).setFixedRows(1),
                cmp.verticalList(
                        cmp.text(textExpression).setStyle(stl.style(Templates.rootStyle).setPadding(2))
                )
        ).setStyle(stl.style(stl.penThin())).setWidth(50);
    }

    private void addGroup(Group group) {
        report.addGroup(group.getGroup().headerWithSubtotal())
                .subtotalsAtGroupHeader(group.getGroup(), group.getSubtotalsHeader().toArray(new SubtotalBuilder[0]))
                .subtotalsAtGroupFooter(group.getGroup(), group.getSubtotalsFooter().toArray(new SubtotalBuilder[0]))
                ;
    }
}
