/*
 * Copyright (c) 2009 TouK.pl
 */
package pl.touk.smx4.examples.service.impl;

import pl.touk.smx4.examples.service.SimpleService;

/**
 * @author mproch
 *
 */
public class SimpleServiceImpl implements SimpleService {

    public String getServiceState(String arg) {
        return "service: "+arg+" is in a bad state :P";
    }

}
