/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.route;

import org.apache.camel.language.XPath;

/**
 * @author mproch
 *
 */
public class CustomChecker {

    public boolean shouldCheck(@XPath("//value") String value) {
        return Integer.parseInt(value) % 2 == 0;
    }
    
    
}
