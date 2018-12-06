package com.mycompany.flightapp.controller;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.flightapp.service.Flight;
import com.mycompany.flightapp.service.FlightSchedulingServiceInterface;
import com.mycompany.flightapp.service.OperatingInstructions;

@RestController
public class FlightsSchedulingController {

	@Autowired
	private FlightSchedulingServiceInterface flightSchedulingService;

	@GetMapping("/flightplan")
	public List<Flight> findAllFlightPlans(@RequestParam(name = "airport", required = false) String airport) {

		if (airport != null) {
			
			return flightSchedulingService.findAllFlightPlansByAirport(airport);
		}
		
		return flightSchedulingService.findAllFlightPlans();
		

	}


	@GetMapping("/operationsplan")

	public List<OperatingInstructions> findOperatingInstructionByRegistration(
			@NotBlank @NotNull @RequestParam("registration") String registration) {

		return flightSchedulingService.findAllOperatingInstructionsByRegistration(registration);
	}

}
