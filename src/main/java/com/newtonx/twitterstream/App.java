package com.newtonx.twitterstream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class App 
{

    public static void main(String[] args) {    	
        SpringApplication.run(App.class, args);
    }
}
