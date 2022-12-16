package com.example.distancecalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

/**
 * Class handling the reading of the data from CSV
 * each line of the CSV is saved as a Station.
 */
public class CSVReader {
    /**
     * This function is used to cheque if this drink can be parsed to a numeric value
     *
     * @param str - String that needs to be checked
     * @return Returns true if string is a numeric value returns false if it's not
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * This function reads data out of CSV file, creates a station object for each line and returns a list of all stations
     *
     * @param fileName name of the CSV file
     * @return returns a List of all Stations
     */
    public HashMap<String, Station> readStationsFromCSV(String fileName) {
        HashMap<String, Station> stations = new HashMap<>();
        Path pathOfFile = Paths.get(fileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathOfFile, StandardCharsets.UTF_8)) {
            String line = bufferedReader.readLine();
            while (line != null) { //reading line by line until EOF
                String[] data = line.split(";"); //split every cell with a semicolon as it's an unused character in this dataset
                Station station = createStation(data); //create a Station and add it to the list
                stations.put(station.getDS100(), station);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stations;
    }

    /**
     * This function creates a Station object based on the given data.
     * The data array is structured the following:
     * data[0]: EVR_NR - Number of the station
     * data[1]: DS100 - place of service
     * <p>
     * data[2]: IFOPT - station key
     * data[3]: name
     * data[4]: traffic - type of traffic RV, FV, DPN
     * data[5]: longitude
     * data[6]: latitude
     * data[7]: operatorName
     * data[8]: operatorID
     * data[9]: status - often empty
     *
     * @param data - See description above
     * @return - Returns The freshly created station based on the given data
     */
    Station createStation(String[] data) {
        //init a station with null attributes for creation
        Station newStation = new Station(null, null, null, null, null
                , null, null, null, null, null);
        //validate data
        if (isNumeric(data[0])) {
            newStation.setEVA_NR(Integer.parseInt(data[0]));
        }
        if (isNumeric(data[8])) {
            newStation.setOperatorID(Integer.parseInt(data[8]));
        }
        if (isNumeric(data[5].replace(",", "."))) { //replace comma with dots as it's needed for correct parsing
            newStation.setLongitude(Double.parseDouble(data[5].replace(",", ".")));
        }
        if (isNumeric(data[6].replace(",", "."))) {
            newStation.setLatitude(Double.parseDouble(data[6].replace(",", ".")));

        }
        newStation.setDS100(data[1]);
        newStation.setIFOPT(data[2]);
        newStation.setName(data[3]);
        newStation.setTraffic(data[4]);
        newStation.setOperatorName(data[7]);

        if (data.length < 10) {
            newStation.setStatus(null);
        } else {
            newStation.setStatus(data[9]);
        }
        //return new station object
        return newStation;
    }
}