/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alisoftclub.frameworks.qsb.dto;

import com.alisoftclub.frameworks.qsb.dto.pojo.Foo;
import javax.script.ScriptException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;

/**
 *
 * @author mohammad
 */
public class TestClass {

    public static void main(String[] args) throws ScriptException {
//        Map<String,Object> entity = new HashMap();
//        entity.put("id", 1l);
//        entity.put("title", "Title");
//        entity.put("rate", 10f);
//        entity.put("qty", 10f);
        Foo foo = new Foo();
        foo.setTitle("Foo");
        SpelParserConfiguration config = new SpelParserConfiguration(true, true);
//
//        StandardEvaluationContext context = new StandardEvaluationContext(foo);
//
        ExpressionParser parser = new SpelExpressionParser(config);
        Expression fooBar = parser.parseExpression("title?:'NO'");
        Expression rate = parser.parseExpression("bar.rate");
        Expression qty = parser.parseExpression("bar.qty");
        Expression amount = parser.parseExpression("bar.amount");
//        
//        rate.setValue(context, 10);
//        qty.setValue(context, 10);
//        
        System.out.println(fooBar.getValue(foo));
    }
}
