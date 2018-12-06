package com.mycompany.flightapp.repository;

import java.util.List;
import java.util.Map;

public interface FlightRepositoryInterface {

	/**
	 * Returns schedules for each take off destination
	 * @return pairs of take off place and schedules for the same.
	 */
	public Map<String, List<FlightSchedule>> findFlightSchedules();
	
	/**
	 * Returns pair of starting point and carrier.
	 * @return pair of starting point and carrier.
	 */
	public Map<String, String> findFlightStartingPoints();
}