package com.mycompany.flightapp.api.integrationtest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.mycompany.flightapp.bootstrap.FlightSchedulingApplication;
import com.mycompany.flightapp.service.Flight;
import com.mycompany.flightapp.service.OperatingInstructions;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = FlightSchedulingApplication.class)
public class FlightSchedulingIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnValidFlightSchedules() {

		ResponseEntity<List<Flight>> flightResponse = restTemplate.exchange("/flightplan", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Flight>>() {
				});

		assertThat(flightResponse.getBody()).anyMatch(flight -> {
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
		ResponseEntity<List<Flight>> flightResponse = restTemplate.exchange("/flightplan?airport=TXL", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Flight>>() {
				});

		assertThat(flightResponse.getBody()).allMatch(flight -> {
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
		ResponseEntity<List<OperatingInstructions>> operationInstructionsResponse = restTemplate.exchange(
				"/operationsplan?registration=A321", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<OperatingInstructions>>() {
				});

		assertThat(operationInstructionsResponse.getBody()).allMatch(operatingInstruction -> {
			return operatingInstruction.getOrigin().equalsIgnoreCase("MUH");
		}).anyMatch(operatingInstruction -> {
			return operatingInstruction.getDestination().equalsIgnoreCase("TXL")
					|| operatingInstruction.getDestination().equalsIgnoreCase("HAM")
					|| operatingInstruction.getDestination().equalsIgnoreCase("MUH")
					|| operatingInstruction.getDestination().equalsIgnoreCase("LHR");
		}).allMatch(operatingInstruction -> {
			return operatingInstruction.getDeparture() != null;
		});
	}

}
