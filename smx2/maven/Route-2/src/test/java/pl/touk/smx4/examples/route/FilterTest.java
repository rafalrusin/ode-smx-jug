/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

/**
 * @author mproch
 *
 */
public class FilterTest extends CamelTestSupport {

    MockEndpoint mock;
    
    @Before
    public void testFilter() throws Exception {
        context = createCamelContext();
        context.addRoutes(new FilterRouter());
        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:test2").to("mock:test");
            }
        });
        context.start();
        mock = context.getEndpoint("mock:test", MockEndpoint.class);
    }

    @Test
    public void testSuccess() throws Exception {
        context.createProducerTemplate().sendBody("direct:test1","<message>ala</message>");
        mock.expectedMessageCount(1);
        mock.assertIsSatisfied();
    }
    
    @Test
    public void testFailure() throws Exception {
        context.createProducerTemplate().sendBody("direct:test1","<message>bela</message>");
        mock.expectedMessageCount(0);
        mock.assertIsSatisfied();
    }
    
}
