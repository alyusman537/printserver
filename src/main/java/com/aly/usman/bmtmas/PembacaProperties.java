package com.aly.usman.bmtmas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("file:printer.properties") 
public class PembacaProperties {
    @Autowired
    private Environment env;

    public String getPortPrinter() {
        return env.getProperty("portPrinter");
    }
}
