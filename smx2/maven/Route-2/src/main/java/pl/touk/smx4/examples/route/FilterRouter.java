/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author mproch
 *
 */
public class FilterRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:test1").filter().xpath("//message/text() = 'ala'").to("direct:test2");
        
    }

}
