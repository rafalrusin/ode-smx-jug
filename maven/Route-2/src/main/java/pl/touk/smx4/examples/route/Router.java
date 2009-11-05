/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.route;

import org.apache.camel.builder.RouteBuilder;

/**
 * @author mproch
 * 
 */
public class Router extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("jbi:service:http://touk.pl/serviceChecker").choice()
                .when().method("customChecker", "shouldCheck")
                    .setBody().xpath("//content").beanRef("simpleService", "getServiceState")
                .otherwise()
                    .setBody(constant("didn't check"))
                 .end()
                 .transform().simple("<answer>${body}</answer>");
    }

}
