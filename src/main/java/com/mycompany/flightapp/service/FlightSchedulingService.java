package com.mycompany.flightapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.flightapp.repository.FlightRepositoryInterface;
import com.mycompany.flightapp.repository.FlightSchedule;
import com.mycompany.flightapp.repository.FlightsRepository;
import com.mycompany.flightapp.repository.FlightsRepository.ScheduleStatus;

@Service
public class FlightSchedulingService implements FlightSchedulingServiceInterface {

	@Autowired
	private FlightRepositoryInterface flightRepository;

	private List<Flight> flights;

	private Map<String, List<Flight>> takeOffPlaceToFlightMap = new HashMap<>();

	Map<String, List<Flight>> flightsByRegistration = new HashMap<>();

	public List<Flight> findAllFlightPlans() {

		return flights;

	}

	public List<Flight> findAllFlightPlansByAirport(String airport) {

		return takeOffPlaceToFlightMap.get(airport);

	}

	public List<OperatingInstructions> findAllOperatingInstructionsByRegistration(String registration) {

		return flightsByRegistration.get(registration).stream().map(flight -> {
			return toOperationInstructions(flight);
		}).collect(Collectors.toList());

	}

	@PostConstruct
	public void prepareFlightSchedule() {
		Map<String, List<FlightSchedule>> airportSchedules = flightRepository.findFlightSchedules();
		Map<String, Stack<FlightSchedule>> flightCarrierSchedules = new HashMap<>();

		flightRepository.findFlightStartingPoints().forEach((startingPoint, carrier) -> {

			Stack<FlightSchedule> flightSchedules = addFlightForStartingPoints(airportSchedules, startingPoint);

			flightCarrierSchedules.put(carrier, flightSchedules);
		});

		flightCarrierSchedules.forEach((carrier, flightSchedules) -> {

			FlightSchedule lastSchedule = flightSchedules.peek();

			lastSchedule = findAllPossibleSchedulesForGivenPlane(airportSchedules, flightSchedules, lastSchedule);

		});

		this.flights = convertFlightScheduleToFlight(flightCarrierSchedules);

		this.takeOffPlaceToFlightMap = prepareTakeoffToFlightDetails(this.flights);

		this.flightsByRegistration = prepareFlightToRegistrationMap(this.flights);

	}

	private FlightSchedule findAllPossibleSchedulesForGivenPlane(Map<String, List<FlightSchedule>> airportSchedules,
			Stack<FlightSchedule> flightSchedules, FlightSchedule lastSchedule) {
		while (lastSchedule != null) {

			for (FlightSchedule fs : airportSchedules.get(lastSchedule.getDestination())) {
				if (fs.isValidScheduleAfter(lastSchedule.getTimeOfArrival())) {
					fs.setOrigin(lastSchedule.getDestination());
					fs.setStatus(ScheduleStatus.OCCUPIED);
					flightSchedules.push(fs);
					lastSchedule = fs;
					break;
				}
			}

			lastSchedule = null;
		}
		return lastSchedule;
	}

	private List<Flight> convertFlightScheduleToFlight(Map<String, Stack<FlightSchedule>> flightCarrierSchedules) {
		final List<Flight> flights = new ArrayList<>();

		flightCarrierSchedules.forEach((carrier, flightSchedules) -> {

			flightSchedules.forEach(flightSchedule -> {
				flights.add(toFlight(flightSchedule, carrier));
			});

		});
		return flights;
	}

	private Map<String, List<Flight>> prepareFlightToRegistrationMap(final List<Flight> flights) {
		Map<String, List<Flight>> flightsByRegistration = new HashMap<>();

		flights.stream().forEach(flight -> {
			flightsByRegistration.putIfAbsent(flight.getCarrier(), new ArrayList<>());

			flightsByRegistration.get(flight.getCarrier()).add(flight);
		});

		return flightsByRegistration;
	}

	private Map<String, List<Flight>> prepareTakeoffToFlightDetails(final List<Flight> flights) {
		Map<String, List<Flight>> takeOffPlaceToFlightMap = new HashMap<>();

		flights.stream().forEach(flight -> {
			takeOffPlaceToFlightMap.putIfAbsent(flight.getOrigin(), new ArrayList<>());

			takeOffPlaceToFlightMap.get(flight.getOrigin()).add(flight);
		});

		return takeOffPlaceToFlightMap;
	}

	private Stack<FlightSchedule> addFlightForStartingPoints(Map<String, List<FlightSchedule>> airportSchedules,
			String startingPoint) {
		Stack<FlightSchedule> flightSchedules = new Stack<>();

		airportSchedules.get(startingPoint).get(0).setStatus(ScheduleStatus.OCCUPIED);
		airportSchedules.get(startingPoint).get(0).setOrigin(startingPoint);

		flightSchedules.add(airportSchedules.get(startingPoint).get(0));
		return flightSchedules;
	}

	private Flight toFlight(FlightSchedule flightSchedule, String carrier) {
		Flight flight = new Flight();

		flight.setArrival(flightSchedule.getTimeOfArrival());
		flight.setCarrier(carrier);
		flight.setDeparture(flightSchedule.getDepartureLocalDateTime());
		flight.setDestination(flightSchedule.getDestination());
		flight.setOrigin(flightSchedule.getOrigin());

		return flight;
	}

	private OperatingInstructions toOperationInstructions(Flight flight) {
		return new OperatingInstructions(flight.getOrigin(), flight.getDestination(), flight.getDeparture());

	}

}
