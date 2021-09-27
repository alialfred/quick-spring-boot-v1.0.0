/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qdr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;

/**
 *
 * @author AI
 */
final class Group {

    private final ColumnGroupBuilder group;
    private List<AggregationSubtotalBuilder<?>> subtotalsHeader = Collections.EMPTY_LIST;
    private List<AggregationSubtotalBuilder<?>> subtotalsFooter = Collections.EMPTY_LIST;

    public Group(ColumnGroupBuilder group) {
        this.group = group;
    }

    public ColumnGroupBuilder getGroup() {
        return group;
    }

    public List<AggregationSubtotalBuilder<?>> getSubtotalsHeader() {
        return subtotalsHeader;
    }

    public void setSubtotalsHeader(List<AggregationSubtotalBuilder<?>> subtotalsHeader) {
        this.subtotalsHeader = subtotalsHeader;
    }

    public List<AggregationSubtotalBuilder<?>> getSubtotalsFooter() {
        return subtotalsFooter;
    }

    public void setSubtotalsFooter(List<AggregationSubtotalBuilder<?>> subtotalsFooter) {
        this.subtotalsFooter = subtotalsFooter;
    }

    
}
