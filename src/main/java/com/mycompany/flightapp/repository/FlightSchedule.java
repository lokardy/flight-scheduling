package com.mycompany.flightapp.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

import com.mycompany.flightapp.repository.FlightsRepository.ScheduleStatus;

import lombok.Data;

@Data

public class FlightSchedule {
	private String destination;
	private Long travelTimeInHours;
	private Long travelTimeInMins;
	private ScheduleStatus status;
	private LocalDateTime departureLocalDateTime;
	private LocalDateTime timeOfArrival;
	private String origin;

	public FlightSchedule(String destination, Long travelTimeInHours, Long travelTimeInMins, Long timeOfDeparture, Long timeOfDepartureInMins,
			ScheduleStatus status) {
		super();
		this.destination = destination;
		this.travelTimeInHours = travelTimeInHours;
		this.travelTimeInMins = travelTimeInMins;

		this.status = status;

		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDate today = LocalDate.now(ZoneId.of("Europe/Berlin"));
		LocalDateTime todayMidnight = LocalDateTime.of(today, midnight);

		this.departureLocalDateTime = todayMidnight.plusHours(timeOfDeparture).plusMinutes(timeOfDepartureInMins);
		this.timeOfArrival = this.departureLocalDateTime.plusHours(this.getTravelTimeInHours())
				.plusMinutes(this.getTravelTimeInMins());
	}

	public LocalDateTime getTimeOfArrival() {
		return this.timeOfArrival;

	}
	
	public LocalDateTime getTimeOfDeparture() {
		return this.departureLocalDateTime;

	}


	public boolean isValidScheduleAfter(LocalDateTime dateTime) {

		if (this.getStatus() == ScheduleStatus.UNOCCUPIED && this.getTimeOfArrival().isBefore(dateTime)) {
			return true;
		}
		return false;
	}

}