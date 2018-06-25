package org.fran.microservice.search.es.index.test;

import org.apache.lucene.expressions.Expression;
import org.apache.lucene.expressions.js.JavascriptCompiler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author fran
 * @Create date 2018/6/20
 */
public class TestExpression {
    public static void main(String[] args) throws Exception {

        System.out.println(1456749000000l / 86400000);
        System.out.println(new Date().getTime() / 86400000);

        evaluator("min(20,30)");
        evaluator("min(170,30)");

        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long d1 = d.parse("2018-6-21 00:00:00").getTime()/86400000;
        long d2 = d.parse("2018-6-20 00:00:00").getTime()/86400000;
        long d3 = d.parse("2017-6-19 00:00:00").getTime()/86400000;
        long d4 = d.parse("2017-6-10 00:00:00").getTime()/86400000;

        evaluator(" log10("+ (d2 - d1)*-1 +")");
        evaluator(" log10("+ (d3 - d1)*-1 +")");
        evaluator(" log10("+ (d4 - d1)*-1 +")");
    }

    private static void evaluator(String expression) throws Exception {
        Expression evaluator = JavascriptCompiler.compile(expression);

        double actual = evaluator.evaluate(null);
        System.out.println(actual);
    }
}
