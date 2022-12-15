package com.example.distancecalculator;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class DistanceCalculatorApplicationTests {
    /**
     * Tests the createTripInfoJSON method of the DistanceCalculatorApplication class.
     * This test initializes a map of Station objects and adds two Station objects to the map.
     * Then, it calls the createTripInfoJSON method with the DS100 codes of the departure and arrival stations,
     * and verifies that the returned JSON object contains the expected values for the departure and arrival stations,
     * the distance, and the unit of measurement.
     *
     * @see DistanceCalculatorApplication#createTripInfoJSON(String, String)
     */
    @Test
    public void testCreateTripInfoJSON() {
        DistanceCalculatorApplication app = new DistanceCalculatorApplication();
        // Initialize the stations map
        DistanceCalculatorApplication.stations = new HashMap<>();
        // Create two Station objects
        Station depart = new Station(1, 2, "BLS", "IFOPT", "Berlin Central Station", "Traffic", "Operator Name", "Status", 52.52, 13.37);
        Station arrive = new Station(3, 4, "FF", "IFOPT", "Munich Central Station", "Traffic", "Operator Name", "Status", 52.52, 13.37);
        // Add the Station objects to the stations map using their DS100 codes as keys
        DistanceCalculatorApplication.stations.put(depart.getDS100(), depart);
        DistanceCalculatorApplication.stations.put(arrive.getDS100(), arrive);

        // Call the createTripInfoJSON method with the DS100 codes of the departure and arrival stations
        ResponseEntity<JSONObject> response = app.createTripInfoJSON("BLS", "FF");

        // Verify that the method returns a JSON object containing the expected values
        JSONObject tripInfo = response.getBody();
        assertEquals("Berlin Central Station", tripInfo.get("from"));
        assertEquals("Munich Central Station", tripInfo.get("to"));
        assertEquals(0, tripInfo.get("distance"));
        assertEquals("km", tripInfo.get("unit"));
    }

    /**
     * Tests the method for calculating the distance between two stations using the haversine formula.
     * The haversine formula determines the great-circle distance between two points on a sphere given their longitudes and latitudes.
     * This is important in navigation, and is a special case of a more general formula in spherical trigonometry, the law of haversine.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine formula</a>
     */
    @Test
    public void testCalculateDistanceBetweenCoordinates() {
        DistanceCalculatorApplication app = new DistanceCalculatorApplication();
        // Create two Station objects
        Station start = new Station(1, 2, "BLS", "IFOPT", "Berlin Central Station", "Traffic", "Operator Name", "Status", 13.369545, 52.525592);
        Station destination = new Station(3, 4, "FF", "IFOPT", "Frankfurt Central Station", "Traffic", "Operator Name", "Status", 8.663789, 50.107145);

        // Calculate the distance between the two stations using the calculateDistanceBetweenCoordinates method
        int distance = app.calculateDistanceBetweenCoordinates(start, destination);

        // Verify that the calculated distance is correct
        assertEquals(423, distance);
    }
}