package com.mycompany.flightapp.service;

import java.util.List;

public interface FlightSchedulingServiceInterface {

	/**
	 * Returns all the flight that take off from the given airport
	 * @param airport
	 * @return Collection of {@link Flight}
	 */
	public List<Flight> findAllFlightPlansByAirport(String airport);
	
	/**
	 * Returns operating plan for the given aircraft carrier
	 * @param registration
	 * @return Collection of {@link OperatingInstructions}
	 */
	public List<OperatingInstructions> findAllOperatingInstructionsByRegistration(String registration);
	
	/**
	 * Returns flight schedules. 
	 * @return list of flight schedules {@link Flight}
	 */
	public List<Flight> findAllFlightPlans() ;
	
}