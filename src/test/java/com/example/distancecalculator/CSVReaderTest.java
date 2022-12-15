package com.example.distancecalculator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CSVReaderTest {
    @Test
    public void testIsNumeric() {
        // Test that the isNumeric method returns true for numeric strings
        assertTrue(CSVReader.isNumeric("123"));
        assertTrue(CSVReader.isNumeric("123.456"));
        assertTrue(CSVReader.isNumeric("0"));
        assertTrue(CSVReader.isNumeric("-123"));
        assertTrue(CSVReader.isNumeric("-123.456"));

        // Test that the isNumeric method returns false for non-numeric strings
        assertFalse(CSVReader.isNumeric("abc"));
        assertFalse(CSVReader.isNumeric("123abc"));
        assertFalse(CSVReader.isNumeric("abc123"));
        assertFalse(CSVReader.isNumeric(""));
        assertFalse(CSVReader.isNumeric(null));
    }

    @Test
    public void testCreateStation() {
        // Define the data for a sample station
        String[] data = {
                "1", "DS100", "IFOPT", "Berlin Central Station", "Traffic", "52.52", "13.37", "Operator Name", "2", "Status"
        };

        // Create an instance of the CSVReader class
        CSVReader reader = new CSVReader();
        // Create a station using the sample data
        Station station = reader.createStation(data);

        // Verify that the values of the fields in the station are correct
        assertEquals(1, station.getEVA_NR().intValue());
        assertEquals(2, station.getOperatorID().intValue());
        assertEquals("DS100", station.getDS100());
        assertEquals("IFOPT", station.getIFOPT());
        assertEquals("Berlin Central Station", station.getName());
        assertEquals("Traffic", station.getTraffic());
        assertEquals("Operator Name", station.getOperatorName());
        assertEquals("Status", station.getStatus());
        assertEquals(52.52, station.getLongitude(), 0.001);
        assertEquals(13.37, station.getLatitude(), 0.001);
    }

    @Test
    public void testReadStationsFromCSV() {
        // Define the path of the CSV file containing the data
        String fileName = "src/D_Bahnhof_2020_alle.csv";

        // Create an instance of the CSVReader class
        CSVReader reader = new CSVReader();

        // Read the stations from the CSV file
        HashMap<String, Station> stations = reader.readStationsFromCSV(fileName);

        // Verify that the stations were read correctly from the CSV file
        assertEquals(6519, stations.size());
        assertEquals("Frankfurt(Main)Hbf", stations.get("FF").getName());
        assertEquals("Berlin Hbf", stations.get("BLS").getName());
    }
}