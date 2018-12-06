package com.mycompany.flightapp.service.test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.flightapp.bootstrap.FlightSchedulingApplication;
import com.mycompany.flightapp.service.Flight;
import com.mycompany.flightapp.service.FlightSchedulingService;
import com.mycompany.flightapp.service.OperatingInstructions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = FlightSchedulingApplication.class)
public class FlightSchedulingServiceTest {

	@Autowired
	private FlightSchedulingService flightSchedulingService;

	@Test
	public void shouldReturnValidFlightSchedules() {
		List<Flight> flights = flightSchedulingService.findAllFlightPlans();

		assertThat(flights).anyMatch(flight -> {
			return flight.getOrigin().equalsIgnoreCase("TXL") || flight.getOrigin().equalsIgnoreCase("HAM")
					|| flight.getOrigin().equalsIgnoreCase("MUH") || flight.getOrigin().equalsIgnoreCase("LHR");
		}).anyMatch(flight -> {
			return flight.getDestination().equalsIgnoreCase("TXL") || flight.getDestination().equalsIgnoreCase("HAM")
					|| flight.getDestination().equalsIgnoreCase("MUH")
					|| flight.getDestination().equalsIgnoreCase("LHR");
		}).allMatch(flight -> {
			return flight.getArrival() != null && flight.getDeparture() != null;
		});
	}
	
	
	@Test
	public void shouldReturnValidFlightSchedulesForGivenAirport() {
		List<Flight> flights = flightSchedulingService.findAllFlightPlansByAirport("TXL");

		assertThat(flights).allMatch(flight -> {
			return flight.getOrigin().equalsIgnoreCase("TXL");
		}).anyMatch(flight -> {
			return flight.getDestination().equalsIgnoreCase("TXL") || flight.getDestination().equalsIgnoreCase("HAM")
					|| flight.getDestination().equalsIgnoreCase("MUH")
					|| flight.getDestination().equalsIgnoreCase("LHR");
		}).allMatch(flight -> {
			return flight.getArrival() != null && flight.getDeparture() != null;
		});
	}
	
	
	@Test
	public void shouldReturnValidOperationsInstructionForGivenRegistration() {
		List<OperatingInstructions> ÖperatingInstructions = flightSchedulingService.findAllOperatingInstructionsByRegistration("A321");

		assertThat(ÖperatingInstructions).allMatch(operatingInstruction -> {
			return operatingInstruction.getOrigin().equalsIgnoreCase("MUH");
		}).anyMatch(operatingInstruction -> {
			return operatingInstruction.getDestination().equalsIgnoreCase("TXL") || operatingInstruction.getDestination().equalsIgnoreCase("HAM")
					|| operatingInstruction.getDestination().equalsIgnoreCase("MUH")
					|| operatingInstruction.getDestination().equalsIgnoreCase("LHR");
		}).allMatch(operatingInstruction -> {
			return  operatingInstruction.getDeparture() != null;
		});
	}

}
