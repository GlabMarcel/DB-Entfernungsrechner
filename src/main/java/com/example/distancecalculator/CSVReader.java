package com.example.distancecalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handling the reading of the data from CSV
 * each line of the CSV is saved as a Station.
 */
public class CSVReader {
    /**
     * This function reads data out of CSV file, creates a station object for each line and returns a list of all stations
     * @param fileName name of the CSV file
     * @return returns a List of all Stations
     */
    public List<Station> readStationsFromCSV(String fileName){
        List<Station> stations = new ArrayList<>();
        Path pathOfFile = Paths.get(fileName);
        try (BufferedReader bufferedReader = Files.newBufferedReader(pathOfFile, StandardCharsets.UTF_8)){
            String line = bufferedReader.readLine();
                while (line != null){ //reading line by line untill EOF
                    String[] data = line.split(";"); //split every cell with a semicolon as its a unused character in this dataset
                    Station station = createStation(data); //create a Station and add it to the list
                    stations.add(station);
                    line = bufferedReader.readLine();
                }
            }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stations;
    }

    /**
     * This function creates a Station object based on the given data.
     * The data array is structured the following:
     * data[0]: EVR_NR - Number of the station
     * data[1]: DS100 - place of service
     * data[2]: IFOPT - station key
     * data[3]: name
     * data[4]: traffic - type of traffic RV, FV, DPN
     * data[5]: longitude
     * data[6]: latitude
     * data[7]: operatorName
     * data[8]: operatorID
     * data[9]: status - often empty
     * @param data
     * @return
     */
    private Station createStation(String[] data){
        //init variables for creation of a Station
        Integer EVA_NR = null;
        Integer operatorID = null;
        Double longitude = null;
        Double latitude = null;
        String status = " ";
        //validate data
        if (isNumeric(data[0])){
            EVA_NR = Integer.parseInt(data[0]);
        }
        if (isNumeric(data[8])){
            operatorID = Integer.parseInt(data[8]);
        }
        if (isNumeric(data[5].replace(",","."))){ //replace comma with dots as its needed for correct parsing
            longitude = Double.parseDouble(data[5].replace(",","."));
        }
        if (isNumeric(data[6].replace(",","."))){
            latitude = Double.parseDouble(data[6].replace(",","."));
        }
            String DS100 = data[1];
            String IFOPT = data[2];
            String name = data[3];
            String traffic = data[4];
            String operatorName = data[7];

            if (data.length <= 10){
                status = null;
            }else {
                status = data[9];
            }


        //return new station object
        return new Station(EVA_NR, operatorID, DS100, IFOPT, name, traffic, operatorName, status, longitude, latitude);
    }

    /**
     * This function is used to cheque if this drink can be parsed to a numeric value
     * @param str - String that needs to be checked
     * @return Returns true if string is a numeric value returns false if it's not
     */
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
