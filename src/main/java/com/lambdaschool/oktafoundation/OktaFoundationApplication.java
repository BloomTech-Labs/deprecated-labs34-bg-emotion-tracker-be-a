package com.lambdaschool.oktafoundation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Main class to start the application.
 */
@EnableJpaAuditing
@SpringBootApplication
@PropertySource(value = "file:\\Users\\kayod\\Documents\\oktaconfiguration.properties", ignoreResourceNotFound = true)
@EnableSwagger2
public class OktaFoundationApplication
{
    /**
     * Main method to start the application.
     *
     * @param args Not used in this application.
     */
    public static void main(String[] args)
    {
        SpringApplication.run(OktaFoundationApplication.class,
            args);
    }
}
