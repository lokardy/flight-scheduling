package com.mycompany.flightapp.service;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Flight {
	private String origin;
	private String destination;
	private LocalDateTime departure;
	private LocalDateTime arrival;
	private String carrier;

}