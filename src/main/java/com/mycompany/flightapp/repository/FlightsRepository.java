package com.mycompany.flightapp.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

@Repository
public class FlightsRepository implements FlightRepositoryInterface {

	private Map<String, String> flightToStartingPointsMap = new HashMap<>();
	private Map<String, List<FlightSchedule>> flightSchedules = new HashMap<>();

	public Map<String, String> findFlightStartingPoints() {

		return flightToStartingPointsMap;

	}

	public Map<String, List<FlightSchedule>> findFlightSchedules() {

		return flightSchedules;

	}

	@PostConstruct
	public void fillData() {

		flightToStartingPointsMap.put("TXL", "737");
		flightToStartingPointsMap.put("MUH", "A321");
		flightToStartingPointsMap.put("LHR", "747-400");
		flightToStartingPointsMap.put("HAM", "A320");

		List<FlightSchedule> txlFlightSchedules = new ArrayList<>();

		txlFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 10L,0l ,ScheduleStatus.UNOCCUPIED));
		txlFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 15L,0l , ScheduleStatus.UNOCCUPIED));
		txlFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 16L, 0l,ScheduleStatus.UNOCCUPIED));
		txlFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 18L,0l, ScheduleStatus.UNOCCUPIED));
		txlFlightSchedules.add(new FlightSchedule("HAM", 0L, 40L, 21L, 0l,ScheduleStatus.UNOCCUPIED));

		flightSchedules.put("TXL", txlFlightSchedules);
		
		List<FlightSchedule> muhFlightSchedules = new ArrayList<>();

		muhFlightSchedules.add(new FlightSchedule("LHR", 2L, 0L, 10L, 0L, ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("TXL", 1L, 0L, 13L, 0L,ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("TXL", 1L, 0L, 15L,0L, ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("LHR", 2L, 0L, 15L, 30L,ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("HAM", 1L, 0L, 1L,30L, ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("LHR", 2L, 30L, 18L, 0L,ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("LHR", 2L, 0L, 20L, 0L,ScheduleStatus.UNOCCUPIED));
		muhFlightSchedules.add(new FlightSchedule("TXL", 1L, 0L, 22L, 0L,ScheduleStatus.UNOCCUPIED));

		flightSchedules.put("MUH", muhFlightSchedules);
		
		List<FlightSchedule> lhrFlightSchedules = new ArrayList<>();

		lhrFlightSchedules.add(new FlightSchedule("HAM", 2L, 30L, 9L,0l ,ScheduleStatus.UNOCCUPIED));
		lhrFlightSchedules.add(new FlightSchedule("TXL", 2L, 0L, 12L,0l , ScheduleStatus.UNOCCUPIED));
		lhrFlightSchedules.add(new FlightSchedule("TXL", 2L, 0L, 17L,0l , ScheduleStatus.UNOCCUPIED));
		lhrFlightSchedules.add(new FlightSchedule("MUH", 2L, 0L, 20L,30l, ScheduleStatus.UNOCCUPIED));

		flightSchedules.put("LHR", lhrFlightSchedules);
		
		List<FlightSchedule> humFlightSchedules = new ArrayList<>();

		humFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 10L,0l ,ScheduleStatus.UNOCCUPIED));
		humFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 13L,0l , ScheduleStatus.UNOCCUPIED));
		humFlightSchedules.add(new FlightSchedule("MUH", 1L, 0L, 20L,0l , ScheduleStatus.UNOCCUPIED));

		flightSchedules.put("HAM", humFlightSchedules);
		
	}
	

	public static enum ScheduleStatus {
		OCCUPIED, UNOCCUPIED
	};

}
