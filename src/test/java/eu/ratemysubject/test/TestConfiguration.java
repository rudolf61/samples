/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ratemysubject.test;

import eu.configuration.AppConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author RUUD
 */
public class TestConfiguration {

    @Test
    public void testConfiguration() {
        AppConfig config = AppConfig.getInstance();

        String environment = config.getValue("environment");
        Assert.assertEquals("dev", environment);
        
        String imap = config.getValue("smtp.url.in");
        Assert.assertEquals("imap.gmail.com", imap);

        Integer port = config.getValue("smtp.port.in");
        Assert.assertEquals(Integer.valueOf(993), port);

        Boolean ssl = config.getValue("smtp.ssl");
        Assert.assertEquals(Boolean.TRUE, ssl);

        Integer tomcatPort = config.getValue("tomcat.portNumber");
        Assert.assertEquals(Integer.valueOf(8088), tomcatPort);
    }
}
