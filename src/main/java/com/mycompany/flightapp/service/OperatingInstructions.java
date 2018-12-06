package com.mycompany.flightapp.service;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperatingInstructions {

	private String origin;
	private String destination;
	private LocalDateTime departure;
}