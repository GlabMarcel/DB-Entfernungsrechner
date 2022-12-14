package com.example.distancecalculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DistanceCalculatorApplicationTests {

    @Test
    public void testStation() {
        // Create a new Station object
        Station station = new Station(1, 2, "DS100", "IFOPT", "Berlin Central Station", "Traffic", "Operator Name", "Status", 52.52, 13.37);

        // Verify that the values of the fields are correct
        assertEquals(1, station.getEVA_NR());
        assertEquals(2, station.getOperatorID());
        assertEquals("DS100", station.getDS100());
        assertEquals("IFOPT", station.getIFOPT());
        assertEquals("Berlin Central Station", station.getName());
        assertEquals("Traffic", station.getTraffic());
        assertEquals("Operator Name", station.getOperatorName());
        assertEquals("Status", station.getStatus());
        assertEquals(52.52, station.getLongitude(), 0.001);
        assertEquals(13.37, station.getLatitude(), 0.001);

        // Set the values of some fields
        station.setOperatorID(3);
        station.setLatitude(13.38);

        // Verify that the new values of the fields are correct
        assertEquals(3, station.getOperatorID());
        assertEquals(13.38, station.getLatitude(), 0.001);
    }

}
