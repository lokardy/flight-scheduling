package com.mycompany.flightapp.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.yilu.flightapp.repository", 
		"com.yilu.flightapp.service", "com.yilu.flightapp.advice", "com.yilu.flightapp.controller"})
public class FlightSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightSchedulingApplication.class, args);
	}
}
