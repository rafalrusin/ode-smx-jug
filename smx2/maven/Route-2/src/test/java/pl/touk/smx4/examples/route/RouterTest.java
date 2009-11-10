/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.spi.Registry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Test;

import pl.touk.smx4.examples.service.impl.SimpleServiceImpl;

/**
 * @author mproch
 *
 */
public class RouterTest extends CamelTestSupport {

   
    @Before
    public void start() throws Exception {
        context = createCamelContext();
        //trick to send to jbi endpoints ;) 
        context.removeComponent("jbi");
        context.addComponent("jbi", context.getComponent("direct"));
        
        context.addRoutes(new Router());
        
        MapRegistry m = new MapRegistry(); 
        m.put("customChecker", new CustomChecker());
        m.put("simpleService", new SimpleServiceImpl());
        ((DefaultCamelContext) context).setRegistry(m);
        
        context.start();
    }
    
    private void testOne(String content, String expected) {
        assertEquals(expected,
        context.createProducerTemplate().requestBody("jbi:endpoint:http://touk.pl/serviceChecker/default", content).toString().replaceAll("<.?answer[^>]*>", ""));
    }
    
    @Test
    public void testOdd() throws Exception {
        testOne("<a><content>maaa</content><key>11</key></a>", "didn't check");
    }
    
    @Test
    public void testEven() throws Exception {
        testOne("<a><content>maaa</content><key>12</key></a>", "service: maaa is in a bad state :P");
    }

    /**
     * to jest juz w camel 2.1
     */
    @SuppressWarnings("serial")
    class MapRegistry extends HashMap<String, Object> implements Registry {
        public Object lookup(String name) {
            return super.get(name);
        }
        @SuppressWarnings("unchecked")
        public <T> T lookup(String name, Class<T> type) {
            return (T) super.get(name);
        }
        public <T> Map<String, T> lookupByType(Class<T> type) {
            throw new UnsupportedOperationException();
        }
        
    }
    
}
